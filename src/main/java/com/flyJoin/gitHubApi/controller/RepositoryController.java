package com.flyJoin.gitHubApi.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.flyJoin.gitHubApi.model.Repos;

@Controller
@RequestMapping("/repos")
public class RepositoryController {

	@Autowired
	RestTemplate restTemplate;

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
	@RequestMapping(value="/{id}/{project}/dir/info/**", method=RequestMethod.GET)
	public void repositoryDirectoryInfo(
			@PathVariable("id") String id,
			@PathVariable("project") String project,
			HttpServletRequest request,
			ModelMap model) {

		// Git 저장소 path 구해오기
		String repositoryPath = request.getRequestURI().replace("/repos/"+id+"/"+project+"/dir/info", "");

		List<Repos> repoArrayList = new ArrayList<Repos>();
		String url = "https://api.github.com/repos/realwater/GitHubApi/contents/src/main/java/com/flyJoin/gitHubApi/controller";

		try{
			Repos[] reposList = restTemplate.getForObject(url, Repos[].class);
			repoArrayList = Arrays.asList(reposList);
		}catch( Exception e){
			e.printStackTrace();
		}

		model.clear();
		model.addAttribute(repoArrayList);
	}

	/**
	 * 저장소 파일 정보
	 * 2014.04.16
	 * @author realwater
	 */
	@RequestMapping(value="/{id}/{project}/file/info/**", method=RequestMethod.GET)
	public void repositoryFileInfo(
			@PathVariable("id") String id,
			@PathVariable("project") String project,
			HttpServletRequest request,
			ModelMap model) {

		// Git 저장소 path 구해오기
		String repositoryPath = request.getRequestURI().replace("/repos/"+id+"/"+project+"/file/info", "");

		Repos repo = new Repos();
		String url = "https://api.github.com/repos/realwater/GitHubApi/contents/src/main/java/com/flyJoin/gitHubApi/controller/HomeController.java";

		try{
			repo = restTemplate.getForObject(url, Repos.class);
		}catch( Exception e){
			e.printStackTrace();
		}

		model.clear();
		model.addAttribute(repo);
	}
}
