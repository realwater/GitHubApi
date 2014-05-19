package org.flyJenkins.github.service;

import java.util.List;

import org.flyJenkins.github.command.GitHubRepoCmd;
import org.flyJenkins.github.model.CommitDto;
import org.flyJenkins.github.model.ReposDto;
import org.flyJenkins.github.model.SearchCodeDto;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:META-INF/spring/applicationContext*"})
public class GitHubRepoManagerImplTest {
	
	@Autowired
	private GitHubRepoManager gitHubRepoManager;

	/**
	 * 프로젝트 정보 조회
	 * @param gitHubRepoCmd
	 * @return
	 */
	@Ignore
	@Test
	public void getProjectInfoTest() {
		GitHubRepoCmd gitHubRepoCmd= new GitHubRepoCmd();
		ReposDto reposDto = gitHubRepoManager.getProjectInfo(gitHubRepoCmd);
		Assert.assertNotNull(reposDto);
	}
		
	/**
	 * 프로젝트 내 코드 검색
	 * @param gitHubRepoCmd
	 * @return
	 */
	@Ignore
	@Test
	public void getSearchFileCodeTest() {
		GitHubRepoCmd gitHubRepoCmd= new GitHubRepoCmd();
		SearchCodeDto searchCodeDto = gitHubRepoManager.getSearchFileCode(gitHubRepoCmd);
		Assert.assertNotNull(searchCodeDto);
	}
		
	/**
	 * 프로젝트 커밋 정보 조회
	 * @param gitHubRepoCmd
	 * @return
	 */
	@Ignore
	@Test
	public void getProjectCommitInfoTest() {
		GitHubRepoCmd gitHubRepoCmd= new GitHubRepoCmd();
		List<CommitDto> commitDtoList = gitHubRepoManager.getProjectCommitInfo(gitHubRepoCmd);
		Assert.assertNotNull(commitDtoList);
	}

}
