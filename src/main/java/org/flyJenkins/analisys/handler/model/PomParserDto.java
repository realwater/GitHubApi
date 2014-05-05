package org.flyJenkins.analisys.handler.model;

import java.util.HashMap;

public class PomParserDto {
	
	private String modelVersion;
	
	private String artifactId;
	
	private String name;
	
	private String packaging;
	
	private String version;
	
	private String encoding;
	
	private HashMap<String, String> properties;

	private HashMap<String, String> dependencies;

	public String getModelVersion() {
		return modelVersion;
	}

	public void setModelVersion(String modelVersion) {
		this.modelVersion = modelVersion;
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

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	public HashMap<String, String> getProperties() {
		return properties;
	}

	public void setProperties(HashMap<String, String> properties) {
		this.properties = properties;
	}

	public HashMap<String, String> getDependencies() {
		return dependencies;
	}

	public void setDependencies(HashMap<String, String> dependencies) {
		this.dependencies = dependencies;
	}
}
