package org.flyJenkins.github.model;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value="items")
public class SearchItemDto {

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
