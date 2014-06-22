package org.flyJenkins.analysis.strategy;

import org.flyJenkins.github.command.GitHubRepoCmd;
import org.flyJenkins.github.model.ProjectDto;
import org.flyJenkins.github.model.SearchCodeDto;
import org.flyJenkins.github.service.GitHubRepoManager;

public class JAVAAnalysisStrategy implements ProjectAnalysisStrategy {
	
	private GitHubRepoManager gitHubRepoManager;
	
	public void setGitHubRepoManager(GitHubRepoManager gitHubRepoManager) {
		this.gitHubRepoManager = gitHubRepoManager;
	}

	@Override
	public void getGitAnalysisInfo(GitHubRepoCmd gitHubRepoCmd, ProjectDto projectDto) {
		gitHubRepoCmd.setQuery("pom");
		gitHubRepoCmd.setLanguage("xml");

		// Project가 메이븐인지 체크
		SearchCodeDto searchCodeDto = gitHubRepoManager.getSearchFileCode(gitHubRepoCmd);
		if (searchCodeDto.getTotal_count() > 0) {
			projectDto.setAnalysisChance("Y");
			projectDto.setBuildType("maven");

			// Project가 Spring 인지 체크
			gitHubRepoCmd.setQuery("application");
			gitHubRepoCmd.setLanguage("xml");

			searchCodeDto = gitHubRepoManager.getSearchFileCode(gitHubRepoCmd);
			if (searchCodeDto.getTotal_count() > 0) {
				projectDto.setProjectType("spring");
			}
		}
	}
}
