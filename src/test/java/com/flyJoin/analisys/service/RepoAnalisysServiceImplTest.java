package com.flyJoin.analisys.service;

import java.util.List;

import org.flyJenkins.analisys.model.FileAnalisysDto;
import org.flyJenkins.analisys.model.ProjectCommonDto;
import org.flyJenkins.analisys.model.RepoAnalisysDto;
import org.flyJenkins.analisys.service.FileAnalisysService;
import org.flyJenkins.analisys.service.RepoAnalisysServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thoughtworks.xstream.XStream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:META-INF/spring/applicationContext*"})
public class RepoAnalisysServiceImplTest {

	@Autowired
	private RepoAnalisysServiceImpl repoAnalisysServiceImpl;
	
	@Autowired
	private FileAnalisysService fileAnalisysServiceImpl;
	
	@Autowired
	private XStream xstreamManager;
	
	@Test
	public void analisysStartTest() {		
		RepoAnalisysDto repoAnalisys = new RepoAnalisysDto();
		repoAnalisys.setRepoUrl("https://github.com/realwater/GitHubApi");
		repoAnalisys.setRepoPath("/trunk");
		
		// 저장소에서 파일 목록 뽑아오기
		List<FileAnalisysDto> fileInfoList = repoAnalisysServiceImpl.getRepoAnalisysFileList(repoAnalisys);		
		
		// 파일 목록 리스트 분석
		ProjectCommonDto projectCommonDto = fileAnalisysServiceImpl.getFileAnalisysResult(fileInfoList);
		
		
		
		
	}
	
}