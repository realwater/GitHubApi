package org.flyJenkins.analysis.strategy.model;

import java.util.HashMap;
import java.util.List;

public class WebServletDto {

	private String servletname;

	private String servletclass;

	private List<HashMap<String, String>> initparam;

	private String loadonstartup;

	public String getServletname() {
		return servletname;
	}

	public void setServletname(String servletname) {
		this.servletname = servletname;
	}

	public String getServletclass() {
		return servletclass;
	}

	public void setServletclass(String servletclass) {
		this.servletclass = servletclass;
	}

	public List<HashMap<String, String>> getInitparam() {
		return initparam;
	}

	public void setInitparam(List<HashMap<String, String>> initparam) {
		this.initparam = initparam;
	}

	public String getLoadonstartup() {
		return loadonstartup;
	}

	public void setLoadonstartup(String loadonstartup) {
		this.loadonstartup = loadonstartup;
	}
}
