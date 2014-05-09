package org.flyJenkins.analysis.strategy.model;

import java.util.HashMap;
import java.util.List;

public class WebFilterDto {

	private String filtername;

	private String filterclass;

	private List<HashMap<String, String>> initparam;

	public String getFiltername() {
		return filtername;
	}

	public void setFiltername(String filtername) {
		this.filtername = filtername;
	}

	public String getFilterclass() {
		return filterclass;
	}

	public void setFilterclass(String filterclass) {
		this.filterclass = filterclass;
	}

	public List<HashMap<String, String>> getInitparam() {
		return initparam;
	}

	public void setInitparam(List<HashMap<String, String>> initparam) {
		this.initparam = initparam;
	}
}
