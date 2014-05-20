package org.flyJenkins.github.service;

import java.util.ArrayList;
import java.util.List;

import org.flyJenkins.github.command.GitHubRepoCmd;
import org.flyJenkins.github.model.CommitDto;
import org.flyJenkins.github.model.ReposDto;
import org.flyJenkins.github.model.SearchCodeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import scala.actors.threadpool.Arrays;

public class GitHubRepoManagerImpl implements GitHubRepoManager {

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("#{github['api.repos.url']}")
	private String gitApiReposUrl;
	
	@Value("#{github['api.search.url']}")
	private String gitApiSearchUrl;
	
	/**
	 * 프로젝트 정보 조회
	 * @param gitHubRepoCmd
	 * @return
	 */
	@Override
	public ReposDto getProjectInfo(GitHubRepoCmd gitHubRepoCmd) {
		StringBuffer sbApiUrl = new StringBuffer();
		sbApiUrl.append(gitApiReposUrl);
		sbApiUrl.append("/"+gitHubRepoCmd.getOwner());
		sbApiUrl.append("/"+gitHubRepoCmd.getRepo());
		
		ReposDto repos = new ReposDto();
		try {
			// 확장자가 있으면 Object로 호출
			repos = restTemplate.getForObject(sbApiUrl.toString(), ReposDto.class);
		} catch (final HttpClientErrorException e) {
		    e.getResponseBodyAsString();
		}
		
		return repos;
	}

	/**
	 * 프로젝트 내 코드 검색
	 * @param gitHubRepoCmd
	 * @return
	 */
	@Override
	public SearchCodeDto getSearchFileCode(GitHubRepoCmd gitHubRepoCmd) {
		StringBuffer sbApiUrl = new StringBuffer();
		sbApiUrl.append(gitApiSearchUrl);
		sbApiUrl.append("/code");
		sbApiUrl.append("?q="+gitHubRepoCmd.getQuery());
		sbApiUrl.append(" in:file");
		sbApiUrl.append(" language:"+gitHubRepoCmd.getLanguage());
		sbApiUrl.append(" repo:"+gitHubRepoCmd.getOwner()+"/"+gitHubRepoCmd.getRepo());
		
		SearchCodeDto searchCodeDto = new SearchCodeDto();
		try {
			// 확장자가 있으면 Object로 호출
			searchCodeDto = restTemplate.getForObject(sbApiUrl.toString(), SearchCodeDto.class);
		} catch (final HttpClientErrorException e) {
		    e.getResponseBodyAsString();
		}
		
		return searchCodeDto;
	}
	
	/**
	 * 프로젝트 커밋 정보 조회
	 * @param gitHubRepoCmd
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CommitDto> getProjectCommitInfo(GitHubRepoCmd gitHubRepoCmd) {
		StringBuffer sbApiUrl = new StringBuffer();
		sbApiUrl.append(gitApiReposUrl);
		sbApiUrl.append("/"+gitHubRepoCmd.getOwner());
		sbApiUrl.append("/"+gitHubRepoCmd.getRepo());
		sbApiUrl.append("/commits");
		
		CommitDto[] commitDtos = null;
		List<CommitDto> commitDtoList = new ArrayList<CommitDto>();
		try {
			// 확장자가 있으면 Object로 호출
			commitDtos = restTemplate.getForObject(sbApiUrl.toString(), CommitDto[].class);
		    commitDtoList = Arrays.asList(commitDtos);
		} catch (final HttpClientErrorException e) {
		    e.getResponseBodyAsString();
		}
		
		return commitDtoList;
	}
}
