package org.flyJenkins.gitHub.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@JsonIgnoreProperties(ignoreUnknown = true)
@XStreamAlias("commit")
@JsonRootName(value="items")
public class SearchCodeDto {

	private String name;
	
	private String path;
	
	private String sha;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSha() {
		return sha;
	}

	public void setSha(String sha) {
		this.sha = sha;
	}
}
