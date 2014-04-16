package com.flyJoin.gitHubApi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.flyJoin.gitHubApi.http.RestTemplateRequestManager;
import com.flyJoin.gitHubApi.model.ResultInfo;

@Controller
@RequestMapping("/repos")
public class RepositoryController {

	@Autowired
	RestTemplateRequestManager restTemplateRequestManager;

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
	 * 저장소 목록 정보
	 * 2014.04.16
	 * @author realwater
	 */
	@RequestMapping(value="/{id}/{project}/info/**", method=RequestMethod.GET)
	public void repositoryInfo(
			@PathVariable("id") String id,
			@PathVariable("project") String project,
			HttpServletRequest request,
			ModelMap model) {

		// Git 저장소 path 구해오기
		String repositoryPath = request.getRequestURI().replace("/repos/"+id+"/"+project+"/info", "");

		System.out.println(id);
		System.out.println(project);
		List<HashMap<String,Object>> latestListJson = new ArrayList<HashMap<String,Object>>();
		ResultInfo sendDataResult = new ResultInfo();
		String url = "https://api.github.com/repos/realwater/GitHubApi/contents/src/main/java/com/github/api/controller/HomeController.java";
		try{
			sendDataResult = restTemplateRequestManager.getExchange(url, request);
			if(sendDataResult != null && sendDataResult.getData() != null){
				latestListJson =   (List<HashMap<String,Object>>) sendDataResult.getData();
			}
		}catch( Exception e){
			e.printStackTrace();
		}

		model.clear();
		model.addAttribute(latestListJson);
	}
}
