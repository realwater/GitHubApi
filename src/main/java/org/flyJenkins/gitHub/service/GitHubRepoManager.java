package org.flyJenkins.gitHub.service;

import java.util.List;

import org.flyJenkins.gitHub.command.GitHubRepoCmd;
import org.flyJenkins.gitHub.model.CommitDto;
import org.flyJenkins.gitHub.model.ReposDto;
import org.flyJenkins.gitHub.model.SearchCodeDto;

public interface GitHubRepoManager {
	
	/**
	 * 프로젝트 정보 조회
	 * @param gitHubRepoCmd
	 * @return
	 */
	public ReposDto getProjectInfo(GitHubRepoCmd gitHubRepoCmd);
		
	/**
	 * 프로젝트 내 코드 검색
	 * @param gitHubRepoCmd
	 * @return
	 */
	public List<SearchCodeDto> getSearchFileCode(GitHubRepoCmd gitHubRepoCmd);
		
	/**
	 * 프로젝트 커밋 정보 조회
	 * @param gitHubRepoCmd
	 * @return
	 */
	public List<CommitDto> getProjectCommitInfo(GitHubRepoCmd gitHubRepoCmd);
}
