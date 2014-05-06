package org.flyJenkins.analisys.service;

import java.util.HashMap;
import java.util.List;

import org.flyJenkins.analisys.model.FileAnalisysDto;
import org.flyJenkins.analisys.model.RepoAnalisysDto;
import org.flyJenkins.analisys.service.FileAnalisysService;
import org.flyJenkins.analisys.service.RepoAnalisysServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:META-INF/spring/applicationContext*"})
public class RepoAnalisysServiceImplTest {

	@Autowired
	private RepoAnalisysServiceImpl repoAnalisysServiceImpl;
	
	@Autowired
	private FileAnalisysService fileAnalisysServiceImpl;
	
	@Test
	public void analisysStartTest() {
		RepoAnalisysDto repoAnalisys = new RepoAnalisysDto();
		repoAnalisys.setRepoUrl("https://github.com/realwater/GitHubApi");
		repoAnalisys.setRepoPath("/trunk");
		
		// 저장소에서 파일 목록 뽑아오기
		List<FileAnalisysDto> fileInfoList = repoAnalisysServiceImpl.getRepoAnalisysFileList(repoAnalisys);		
		
		// 파일 목록 리스트 분석
		HashMap<String, Object> fileAnalisysInfo = fileAnalisysServiceImpl.getFileAnalisysResult(fileInfoList);
		
		System.out.println(fileAnalisysInfo);
	}
	
}