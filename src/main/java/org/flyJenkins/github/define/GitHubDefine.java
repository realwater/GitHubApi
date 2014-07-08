package org.flyJenkins.github.define;

import org.springframework.beans.factory.annotation.Value;

public class GitHubDefine {
	
	@Value("#{github['api.url']}")
	public static String GIT_API_URL;

	@Value("#{github['api.repos.url']}")
	public static String GIT_API_REPOS_URL;
	
	@Value("#{github['api.search.url']}")
	public static String GIT_API_SEARCH_URL;
	
	@Value("#{github['github.strategy.package']}")
	public static String GIT_STRATEGY_PACKAGE;
	
	@Value("#{github['github.strategy.name']}")
	public static String GIT_STRATEGY_NAME;
}
