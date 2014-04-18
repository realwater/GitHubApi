package com.flyJoin.gitHubApi.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.flyJoin.gitHubApi.model.Repos;
import com.thoughtworks.xstream.XStream;

@Controller
@RequestMapping("/repos")
public class RepositoryController {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	XStream xstreamManager;

	/**
	 * 저장소 분석
	 * 2014.04.16
	 * @author realwater
	 */
	@RequestMapping(value="/{id}/analysis/{project}/{token}", method=RequestMethod.GET)
	public void analysisRepository(
			@PathVariable("id") String id,
			@PathVariable("project") String project,
			@PathVariable("token") String token) {

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

		Repos repos = new Repos();
		Repos[] reposList = {};

		// 확장자가 있으면 Object로 호출
		if (requestPath.indexOf('.') > 0) {
			xstreamManager.alias("repos", Repos.class);
			repos = restTemplate.getForObject(url, Repos.class);
			resultJson = xstreamManager.toXML(repos);
		} else { // 확장자가 있으면 Object로 호출
			xstreamManager.alias("repos", Repos[].class);
			reposList = restTemplate.getForObject(url, Repos[].class);
			resultJson = xstreamManager.toXML(reposList);
		}

		return resultJson;
	}

}
