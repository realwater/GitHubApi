package org.flyJenkins.github.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.flyJenkins.cache.redis.model.RedisCacheDto;
import org.flyJenkins.cache.redis.service.RedisCacheManager;
import org.flyJenkins.cache.redis.service.RedisCacheManagerImpl;
import org.flyJenkins.github.command.GitHubRepoCmd;
import org.flyJenkins.github.model.CommitDto;
import org.flyJenkins.github.model.ProjectDto;
import org.flyJenkins.github.model.ReposDto;
import org.flyJenkins.github.model.SearchCodeDto;
import org.flyJenkins.github.service.GitHubRepoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.thoughtworks.xstream.XStream;

@Controller
@RequestMapping("/repos")
public class RepositoryController {

	@Autowired
	private GitHubRepoManager gitHubRepoManager;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private XStream xstreamManager;
	
	@Autowired
	private RedisCacheManager redisCacheManagerImpl;

	/**
	 * GIT 저장소 분석
	 * 2014.05.11
	 * @author realwater
	 */
	@RequestMapping(value="/{owner}/{repo}/git/analysis", method=RequestMethod.GET)
	public void gitAnalysisInfo(
			@PathVariable("owner") String owner,
			@PathVariable("repo") String repo,
			HttpServletRequest request,
			ModelMap mode) {
		
		GitHubRepoCmd gitHubRepoCmd= new GitHubRepoCmd();
		gitHubRepoCmd.setOwner(owner);
		gitHubRepoCmd.setRepo(repo);
		
		// Cache Data 조회
		ProjectDto projectDto = (ProjectDto) redisCacheManagerImpl.getCacheValue(owner+"_"+repo);
		Boolean isSync = false;

		// commit 최신 정보와 cache commit 최신 정보가 다를 경우 재 동기화 한다.
		if (projectDto != null) {
			isSync = true;
			List<CommitDto> commitDtoList = gitHubRepoManager.getProjectCommitInfo(gitHubRepoCmd);
			if (!commitDtoList.isEmpty()) {
				if (!projectDto.getCommitSha().equals(commitDtoList.get(0).getSha())) {
					isSync = false;
				}
			}
		}
		
		// Data Analysis 및 Cache 동기화
		if (isSync == false) {
			projectDto = new ProjectDto();
			projectDto.setProjectName(repo);

			// 1. 프로젝트 정보 조회
			ReposDto reposDto = gitHubRepoManager.getProjectInfo(gitHubRepoCmd);

			// 2. 프로젝트 내 타입 조회
			if (!reposDto.getName().equals(null)) {
				String projectLanguage = reposDto.getLanguage().toUpperCase();
				projectDto.setLanguage(projectLanguage);

				SearchCodeDto searchCodeDto = null;
				if (projectLanguage.equals("JAVA")) {
					gitHubRepoCmd.setQuery("pom");
					gitHubRepoCmd.setLanguage("xml");

					// Project가 메이븐인지 체크
					searchCodeDto = gitHubRepoManager.getSearchFileCode(gitHubRepoCmd);
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
				} else if (projectLanguage.equals("JAVASCRIPT")) {
					// Project가 node.js 인지 체크
					gitHubRepoCmd.setQuery("version");
					gitHubRepoCmd.setLanguage("json");

					searchCodeDto = gitHubRepoManager.getSearchFileCode(gitHubRepoCmd);
					if (searchCodeDto.getTotal_count() > 0) {
						projectDto.setAnalysisChance("Y");
						projectDto.setProjectType("node.js");
					}
				}

				// 최신 리비전 sha 정보가 있으면 저장한다.
				List<CommitDto> commitDtoList = gitHubRepoManager.getProjectCommitInfo(gitHubRepoCmd);
				if (!commitDtoList.isEmpty()) {
					projectDto.setCommitSha(commitDtoList.get(0).getSha());
				}				
			}
			
			/**
			 * 캐쉬 데이터 저장
			 */
			RedisCacheDto redisCacheDto = new RedisCacheDto(); 
			redisCacheDto.setChannelKey(owner+"_"+repo);
			redisCacheDto.setValue(projectDto);
			redisCacheDto.setExpireTime(1800);
			redisCacheManagerImpl.saveCacheValue(redisCacheDto);
		}
		
		mode.clear();
		mode.addAttribute("projectDto", projectDto);
	}


	/**
	 * 저장소 디렉토리 정보
	 * 2014.04.16
	 * @author realwater
	 */
	@RequestMapping(value="/{owner}/{repo}/contents/**", method=RequestMethod.GET)
	@ResponseBody
	public String repositoryInfo(
			@PathVariable("owner") String owner,
			@PathVariable("repo") String repo,
			HttpServletRequest request,
			ModelMap model) {

		String requestPath = request.getRequestURI();
		String url = "https://api.github.com"+requestPath;
		String resultJson = null;

		ReposDto repos = new ReposDto();
		ReposDto[] reposList = {};

		try {
			// 확장자가 있으면 Object로 호출
			if (requestPath.indexOf('.') > 0) {
				xstreamManager.alias("repos", ReposDto.class);
				repos = restTemplate.getForObject(url, ReposDto.class);
				resultJson = xstreamManager.toXML(repos);
			} else { // 확장자가 있으면 Object로 호출
				xstreamManager.alias("repos", ReposDto[].class);
				reposList = restTemplate.getForObject(url, ReposDto[].class);
				resultJson = xstreamManager.toXML(reposList);
			}
		} catch (final HttpClientErrorException e) {
		    resultJson = e.getResponseBodyAsString();
		}

		return resultJson;
	}

}
