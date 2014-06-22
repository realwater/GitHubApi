package org.flyJenkins.github.analysis.strategy;

import org.flyJenkins.github.command.GitHubRepoCmd;
import org.flyJenkins.github.model.ProjectDto;
import org.flyJenkins.github.model.SearchCodeDto;
import org.flyJenkins.github.service.GitHubRepoManager;

public class JAVASCRIPTAnalysisStrategy implements GitHubAnalysisStrategy {
	
	private GitHubRepoManager gitHubRepoManager;
	
	public void setGitHubRepoManager(GitHubRepoManager gitHubRepoManager) {
		this.gitHubRepoManager = gitHubRepoManager;
	}
	@Override
	public void getGitAnalysisInfo(GitHubRepoCmd gitHubRepoCmd, ProjectDto projectDto) {
		// Project가 node.js 인지 체크
		gitHubRepoCmd.setQuery("version");
		gitHubRepoCmd.setLanguage("json");

		SearchCodeDto searchCodeDto = gitHubRepoManager.getSearchFileCode(gitHubRepoCmd);
		if (searchCodeDto.getTotal_count() > 0) {
			projectDto.setAnalysisChance("Y");
			projectDto.setProjectType("node.js");
		}
	}

}
