package org.flyJenkins.gitHub.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.flyJenkins.analysis.model.FileAnalysisDto;
import org.flyJenkins.analysis.model.RepoAnalysisDto;
import org.flyJenkins.analysis.service.FileAnalysisService;
import org.flyJenkins.analysis.service.RepoAnalysisService;
import org.flyJenkins.gitHub.model.ProjectDto;
import org.flyJenkins.gitHub.model.ReposDto;
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
	private RepoAnalysisService svnRepoAnalysisServiceImpl;
	
	@Autowired
	private RepoAnalysisService gitRepoAnalysisServiceImpl;

	@Autowired
	private FileAnalysisService fileAnalysisServiceImpl;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private XStream xstreamManager;

	/**
	 * SVN 저장소 분석
	 * 2014.04.16
	 * @author realwater
	 */
	@RequestMapping(value="/{projectName}/{svnUrl}/{path}/svn/analysis", method=RequestMethod.GET)
	public void svnAnalysisInfo(
			@PathVariable("projectName") String projectName,
			@PathVariable("svnUrl") String svnUrl,
			@PathVariable("path") String path,
			HttpServletRequest request,
			ModelMap mode) {

		ProjectDto projectDto = new ProjectDto();

		RepoAnalysisDto repoAnalysisDto = new RepoAnalysisDto();
		repoAnalysisDto.setRepoUrl(svnUrl);
		repoAnalysisDto.setRepoPath("/"+path);

		// 저장소에서 파일 목록 뽑아오기
		List<FileAnalysisDto> fileInfoList = svnRepoAnalysisServiceImpl.getRepoAnalisysFileList(repoAnalysisDto);

		// 파일 목록 리스트 분석
		HashMap<String, Object> fileAnalysisInfo = fileAnalysisServiceImpl.getFileAnalisysResult(fileInfoList);
		projectDto.setProjectName(projectName);
		projectDto.setAnalysisInfo(fileAnalysisInfo);

		mode.clear();
		mode.addAttribute("projectDto", projectDto);
	}
	
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

		ProjectDto projectDto = new ProjectDto();

		StringBuffer repoUrl = new StringBuffer();
		repoUrl.append("https://api.github.com");
		repoUrl.append("/");
		repoUrl.append(owner);
		repoUrl.append("/");
		repoUrl.append(repo);
		repoUrl.append("/contents");

		RepoAnalysisDto repoAnalysisDto = new RepoAnalysisDto();
		repoAnalysisDto.setRepoUrl(repoUrl.toString());

		// 저장소에서 파일 목록 뽑아오기
		List<FileAnalysisDto> fileInfoList = gitRepoAnalysisServiceImpl.getRepoAnalisysFileList(repoAnalysisDto);

		// 파일 목록 리스트 분석
		HashMap<String, Object> fileAnalysisInfo = fileAnalysisServiceImpl.getFileAnalisysResult(fileInfoList);
		projectDto.setProjectName(repo);
		projectDto.setAnalysisInfo(fileAnalysisInfo);

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
