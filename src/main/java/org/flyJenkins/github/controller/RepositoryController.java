package org.flyJenkins.github.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.flyJenkins.cache.redis.model.RedisCacheDto;
import org.flyJenkins.cache.redis.service.RedisCacheManager;
import org.flyJenkins.github.command.GitHubRepoCmd;
import org.flyJenkins.github.model.CommitDto;
import org.flyJenkins.github.model.ProjectDto;
import org.flyJenkins.github.model.ReposDto;
import org.flyJenkins.github.service.GitHubRepoManager;
import org.flyJenkins.github.strategy.GitHubAnalysisStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("#{github['api.url']}")
	public String GIT_API_URL;
	
	@Value("#{github['github.strategy.package']}")
	public String GIT_STRATEGY_PACKAGE;
	
	@Value("#{github['github.strategy.name']}")
	public String GIT_STRATEGY_NAME;
	
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

		String cacheKey = owner+"_"+repo+"_analysis";
		
		// Cache Data 조회
		ProjectDto projectDto = (ProjectDto) redisCacheManagerImpl.getCacheValue(cacheKey);

		// Data Analysis 및 Cache 동기화
		if (projectDto == null) {
			GitHubRepoCmd gitHubRepoCmd = new GitHubRepoCmd();
			gitHubRepoCmd.setOwner(owner);
			gitHubRepoCmd.setRepo(repo);

			// 1. 프로젝트 정보 조회
			ReposDto reposDto = gitHubRepoManager.getProjectInfo(gitHubRepoCmd);

			// 2. 프로젝트 내 타입 조회
			if (reposDto != null) {
				projectDto = new ProjectDto();
				projectDto.setProjectName(repo);
				
				String projectLanguage = reposDto.getLanguage().toUpperCase();
				projectDto.setLanguage(projectLanguage);
				
				StringBuffer sbFileClassName = new StringBuffer();
				sbFileClassName.append(this.GIT_STRATEGY_PACKAGE);
				sbFileClassName.append(projectLanguage);
				sbFileClassName.append(this.GIT_STRATEGY_NAME);
				
				String strategyClassName = sbFileClassName.toString();
				
				// 맞는 분석 클래스가 있을 경우 분석 처리
				if (!strategyClassName.isEmpty()) {
					Class<?> strategyClass;
					try {
						strategyClass = Class.forName(strategyClassName);
						GitHubAnalysisStrategy gitHubAnalysisStrategy = (GitHubAnalysisStrategy) strategyClass.newInstance();
						// call by reference로 projectDto 정보 생성
						gitHubAnalysisStrategy.setGitHubRepoManager(gitHubRepoManager);
						gitHubAnalysisStrategy.getGitAnalysisInfo(gitHubRepoCmd, projectDto);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				// 최신 리비전 sha 정보가 있으면 저장한다.
				List<CommitDto> commitDtoList = gitHubRepoManager.getProjectCommitInfo(gitHubRepoCmd);
				if (!commitDtoList.isEmpty()) {
					projectDto.setCommitSha(commitDtoList.get(0).getSha());
				}		
				
				/**
				 * 캐쉬 데이터 저장
				 */
				RedisCacheDto redisCacheDto = new RedisCacheDto(); 
				redisCacheDto.setChannelKey(cacheKey);
				redisCacheDto.setValue(projectDto);
				redisCacheDto.setExpireTime(1800);
				redisCacheManagerImpl.saveCacheValue(redisCacheDto);
			}
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
		String cacheKey = owner+"/"+repo+requestPath;
		
		// Cache Data 조회
		String resultJson = (String) redisCacheManagerImpl.getCacheValue(cacheKey);
		
		if (resultJson == null) {
			String url = this.GIT_API_URL+requestPath;
			
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
			
			/**
			 * 캐쉬 데이터 저장
			 */
			RedisCacheDto redisCacheDto = new RedisCacheDto(); 
			redisCacheDto.setChannelKey(cacheKey);
			redisCacheDto.setValue(resultJson);
			redisCacheDto.setExpireTime(1800);
			redisCacheManagerImpl.saveCacheValue(redisCacheDto);
		}

		return resultJson;
	}

}
