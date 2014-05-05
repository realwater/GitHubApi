package org.flyJenkins.gitHub.model;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.junit.Before;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("project")
public class ProjectDto {
	
	private String projectName;
		
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

	public HashMap<String, Object> getAnalisysInfo() {
		return analisysInfo;
	}

	public void setAnalisysInfo(HashMap<String, Object> analisysInfo) {
		this.analisysInfo = analisysInfo;
	}
}