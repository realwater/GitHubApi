package org.flyJenkins.analysis.strategy;

import org.flyJenkins.github.command.GitHubRepoCmd;
import org.flyJenkins.github.model.ProjectDto;
import org.flyJenkins.github.service.GitHubRepoManager;

public interface ProjectAnalysisStrategy {
	
	public void setGitHubRepoManager(GitHubRepoManager gitHubRepoManager);

	public void getGitAnalysisInfo(GitHubRepoCmd gitHubRepoCmd, ProjectDto projectDto);
}