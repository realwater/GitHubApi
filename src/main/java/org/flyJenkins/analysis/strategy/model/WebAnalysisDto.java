package org.flyJenkins.analysis.strategy.model;

import java.util.HashMap;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("webAnalysis")
public class WebAnalysisDto {

	private String version;

	private List<WebFilterDto> filter;

	private List<WebServletDto> servlet;

	private List<HashMap<String, String>> contextparam;

	private List<HashMap<String, String>> servletmapping;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<WebFilterDto> getFilter() {
		return filter;
	}

	public void setFilter(List<WebFilterDto> filter) {
		this.filter = filter;
	}

	public List<WebServletDto> getServlet() {
		return servlet;
	}

	public void setServlet(List<WebServletDto> servlet) {
		this.servlet = servlet;
	}

	public List<HashMap<String, String>> getContextparam() {
		return contextparam;
	}

	public void setContextparam(List<HashMap<String, String>> contextparam) {
		this.contextparam = contextparam;
	}

	public List<HashMap<String, String>> getServletmapping() {
		return servletmapping;
	}

	public void setServletmapping(List<HashMap<String, String>> servletmapping) {
		this.servletmapping = servletmapping;
	}
}