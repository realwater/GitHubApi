package org.flyJenkins.gitHub.service;

import java.util.ArrayList;
import java.util.List;

import org.flyJenkins.gitHub.command.GitHubRepoCmd;
import org.flyJenkins.gitHub.model.CommitDto;
import org.flyJenkins.gitHub.model.ReposDto;
import org.flyJenkins.gitHub.model.SearchCodeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import scala.actors.threadpool.Arrays;

public class GitHubRepoManagerImpl implements GitHubRepoManager {

	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * 프로젝트 정보 조회
	 * @param gitHubRepoCmd
	 * @return
	 */
	@Override
	public ReposDto getProjectInfo(GitHubRepoCmd gitHubRepoCmd) {
		String url = "https://api.github.com/repos/realwater/GitHubApi";
		ReposDto repos = new ReposDto();

		try {
			// 확장자가 있으면 Object로 호출
			repos = restTemplate.getForObject(url, ReposDto.class);
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
	@SuppressWarnings("unchecked")
	@Override
	public List<SearchCodeDto> getSearchFileCode(GitHubRepoCmd gitHubRepoCmd) {
		String url = "https://api.github.com/search/code?q=pom+in:file+language:xml+repo:realwater/GitHubApi";
		SearchCodeDto[] searchCodeDtos = null;
		List<SearchCodeDto> searchCodeDtoList = new ArrayList<SearchCodeDto>();
		
		try {
			// 확장자가 있으면 Object로 호출
			searchCodeDtos = restTemplate.getForObject(url, SearchCodeDto[].class);
			searchCodeDtoList = Arrays.asList(searchCodeDtos);
		} catch (final HttpClientErrorException e) {
		    e.getResponseBodyAsString();
		}
		
		return searchCodeDtoList;
	}
	
	/**
	 * 프로젝트 커밋 정보 조회
	 * @param gitHubRepoCmd
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CommitDto> getProjectCommitInfo(GitHubRepoCmd gitHubRepoCmd) {
		String url = "https://api.github.com/repos/realwater/GitHubApi/commits";
		CommitDto[] commitDtos = null;
		List<CommitDto> commitDtoList = new ArrayList<CommitDto>();
		
		try {
			// 확장자가 있으면 Object로 호출
			commitDtos = restTemplate.getForObject(url, CommitDto[].class);
		    commitDtoList = Arrays.asList(commitDtos);
		} catch (final HttpClientErrorException e) {
		    e.getResponseBodyAsString();
		}
		
		return commitDtoList;
	}
}
