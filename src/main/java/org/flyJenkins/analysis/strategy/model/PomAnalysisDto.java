package org.flyJenkins.analysis.strategy.model;

import java.util.HashMap;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("pomAnalysis")
public class PomAnalysisDto {

	private String modelVersion;

	private String groupId;

	private String artifactId;

	private String name;

	private String packaging;

	private String version;

	private HashMap<String, String> properties;

	private List<HashMap<String, String>> dependency;

	public String getModelVersion() {
		return modelVersion;
	}

	public void setModelVersion(String modelVersion) {
		this.modelVersion = modelVersion;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPackaging() {
		return packaging;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public HashMap<String, String> getProperties() {
		return properties;
	}

	public void setProperties(HashMap<String, String> properties) {
		this.properties = properties;
	}

	public List<HashMap<String, String>> getDependency() {
		return dependency;
	}

	public void setDependency(List<HashMap<String, String>> dependency) {
		this.dependency = dependency;
	}
}
