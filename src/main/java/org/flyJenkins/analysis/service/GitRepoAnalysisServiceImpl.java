package org.flyJenkins.analysis.service;

import java.util.ArrayList;
import java.util.List;

import org.flyJenkins.analysis.model.FileAnalysisDto;
import org.flyJenkins.analysis.model.RepoAnalysisDto;
import org.flyJenkins.gitHub.model.ReposDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class GitRepoAnalysisServiceImpl implements RepoAnalysisService {

	private List<FileAnalysisDto> fileInfoList;
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<FileAnalysisDto> getRepoAnalisysFileList(RepoAnalysisDto repoAnalysisDto) {
		fileInfoList = new ArrayList<FileAnalysisDto>();
		repositoryDirectoryLoading(repoAnalysisDto.getRepoUrl());
		return fileInfoList;
	}
	
	/**
	 * 저장소 디렉토리 하위 목록 로드
	 */
	public void repositoryDirectoryLoading(String url) {
		ReposDto repos = new ReposDto();
		ReposDto[] reposList = {};
		try {
			reposList = restTemplate.getForObject(url, ReposDto[].class);
			
			for(ReposDto repoInfo : reposList) {
				if (repoInfo.getType().equals("dir")) {
					repositoryDirectoryLoading(repoInfo.getUrl());
				} else if (repoInfo.getType().equals("file")) {
					repos = restTemplate.getForObject(repoInfo.getUrl(), ReposDto.class);
					
					// File 이름, 확장자 정보 추출
					String fileName = repos.getName();
					int lastExtPos = fileName.lastIndexOf(".");
					String fileExtension = fileName.substring( lastExtPos + 1 );
					fileName = fileName.substring(0, lastExtPos);
	
					// 파일 분석 Service로 보내기
					FileAnalysisDto fileAnalisysDto = new FileAnalysisDto();
					fileAnalisysDto.setFileName(fileName);
					fileAnalisysDto.setFileExtension(fileExtension);
					fileAnalisysDto.setSourceData(repos.getContent());
	
					fileInfoList.add(fileAnalisysDto);
				}
			}
		} catch (final HttpClientErrorException e) {
		}
	}
	
}
