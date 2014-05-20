package org.flyJenkins.github.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@JsonIgnoreProperties(ignoreUnknown = true)
@XStreamAlias("searchCode")
public class SearchCodeDto {
	
	private int total_count;
	
	private List<SearchItemDto> items;

	public List<SearchItemDto> getItems() {
		return items;
	}

	public void setItems(List<SearchItemDto> items) {
		this.items = items;
	}

	public int getTotal_count() {
		return total_count;
	}

	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}
}
