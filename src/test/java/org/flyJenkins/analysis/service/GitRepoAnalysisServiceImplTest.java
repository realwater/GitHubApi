package org.flyJenkins.analysis.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.flyJenkins.analysis.model.FileAnalysisDto;
import org.flyJenkins.analysis.model.RepoAnalysisDto;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:META-INF/spring/applicationContext*"})
public class GitRepoAnalysisServiceImplTest {
	
	@Autowired
	private RepoAnalysisService gitRepoAnalysisServiceImpl;
	
	@Autowired
	private FileAnalysisService fileAnalysisServiceImpl;
	
	@Test
	@Ignore
	public void gitRepoAnalysisStartTest() {
		
		StringBuffer repoUrl = new StringBuffer();
		repoUrl.append("https://api.github.com/repos");
		repoUrl.append("/");
		repoUrl.append("realwater");
		repoUrl.append("/");
		repoUrl.append("GitHubApi");
		repoUrl.append("/contents");

		RepoAnalysisDto repoAnalysisDto = new RepoAnalysisDto();
		repoAnalysisDto.setRepoUrl(repoUrl.toString());

		// 저장소에서 파일 목록 뽑아오기
		List<FileAnalysisDto> fileInfoList = gitRepoAnalysisServiceImpl.getRepoAnalisysFileList(repoAnalysisDto);
		
		for(FileAnalysisDto fileAnalysisDto : fileInfoList) {
			System.out.println(fileAnalysisDto.getFileName());
		}
	}
	
	@Test
	public void encodeTest() {
		
		String value = "https://github.com";
		
		try {
			System.out.println(URLEncoder.encode(value, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
