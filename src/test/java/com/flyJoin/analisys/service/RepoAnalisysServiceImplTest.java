package com.flyJoin.analisys.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.flyJoin.analisys.command.RepoAnalisysCommand;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:META-INF/spring/applicationContext*"})
public class RepoAnalisysServiceImplTest {

	@Autowired
	private RepoAnalisysServiceImpl repoAnalisysServiceImpl;
	
	@Test
	public void analisysStartTest() {		
		RepoAnalisysCommand repoAnalisys = new RepoAnalisysCommand();
		repoAnalisys.setRepoUrl("https://github.com/mapbox/mapbox-ios-example");
		repoAnalisys.setRepoPath("/trunk");
		
		repoAnalisysServiceImpl.analisysStart(repoAnalisys);
	}
}
