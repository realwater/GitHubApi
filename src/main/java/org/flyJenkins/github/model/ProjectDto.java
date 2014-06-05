package org.flyJenkins.github.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@JsonIgnoreProperties(ignoreUnknown = true)
@XStreamAlias("project")
public class ProjectDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4369623234713558093L;

	private String projectName;

	private String analysisChance = "N";

	private String language;

	private String buildType;

	private String projectType;

	private String commitSha;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getAnalysisChance() {
		return analysisChance;
	}

	public void setAnalysisChance(String analysisChance) {
		this.analysisChance = analysisChance;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getBuildType() {
		return buildType;
	}

	public void setBuildType(String buildType) {
		this.buildType = buildType;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getCommitSha() {
		return commitSha;
	}

	public void setCommitSha(String commitSha) {
		this.commitSha = commitSha;
	}
}