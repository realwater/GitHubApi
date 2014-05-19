package org.flyJenkins.gitHub.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@JsonIgnoreProperties(ignoreUnknown = true)
@XStreamAlias("commit")
public class CommitDto {

	private String sha;

	public String getSha() {
		return sha;
	}

	public void setSha(String sha) {
		this.sha = sha;
	}
}
