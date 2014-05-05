package org.flyJenkins.gitHub.model;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.junit.Before;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("project")
public class ProjectCommonDto {
	
	private String projectName;

	private String projectType;
		
	HashMap<String, Object> analisysInfo;

	@Before
	public void init() {
		this.analisysInfo = new HashMap<String, Object>();
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public HashMap<String, Object> getAnalisysInfo() {
		return analisysInfo;
	}

	public void setAnalisysInfo(HashMap<String, Object> analisysInfo) {
		analisysInfo.putAll(analisysInfo);
	}
}