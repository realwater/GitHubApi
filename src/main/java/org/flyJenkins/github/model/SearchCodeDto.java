package org.flyJenkins.github.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("searchCode")
public class SearchCodeDto {
	
	@JsonProperty(value="total_count")
	private int totalcount;
	
	@JsonProperty(value="items")
	private List<SearchItemDto> items;

	public int getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}

	public List<SearchItemDto> getItems() {
		return items;
	}

	public void setItems(List<SearchItemDto> items) {
		this.items = items;
	}
}
