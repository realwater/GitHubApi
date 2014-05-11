package org.flyJenkins.analysis.service;

import java.util.HashMap;
import java.util.List;

import org.flyJenkins.analysis.model.FileAnalysisDto;
import org.flyJenkins.analysis.model.RepoAnalysisDto;
import org.flyJenkins.analysis.service.FileAnalysisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:META-INF/spring/applicationContext*"})
public class RepoAnalisysServiceImplTest {

	@Autowired
	private RepoAnalysisService svnRepoAnalysisServiceImpl;

	@Autowired
	private FileAnalysisService fileAnalysisServiceImpl;

	@Test
	public void svnRepoAnalysisStartTest() {
		RepoAnalysisDto repoAnalysisDto = new RepoAnalysisDto();
		repoAnalysisDto.setRepoUrl("https://github.com/realwater/GitHubApi");
		repoAnalysisDto.setRepoPath("/trunk");

		// 저장소에서 파일 목록 뽑아오기
		List<FileAnalysisDto> fileInfoList = svnRepoAnalysisServiceImpl.getRepoAnalisysFileList(repoAnalysisDto);

		// 파일 목록 리스트 분석
		HashMap<String, Object> fileAnalysisInfo = fileAnalysisServiceImpl.getFileAnalisysResult(fileInfoList);

		System.out.println(fileAnalysisInfo);
	}

}