package org.flyJenkins.gitHub.model;

import java.util.HashMap;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.junit.Before;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@JsonIgnoreProperties(ignoreUnknown = true)
@XStreamAlias("project")
public class ProjectDto {

	private String projectName;

	HashMap<String, Object> analysisInfo;

	@Before
	public void init() {
		this.analysisInfo = new HashMap<String, Object>();
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public HashMap<String, Object> getAnalysisInfo() {
		return analysisInfo;
	}

	public void setAnalysisInfo(HashMap<String, Object> analysisInfo) {
		this.analysisInfo = analysisInfo;
	}
}