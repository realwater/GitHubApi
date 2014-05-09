package org.flyJenkins.controller.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("response")
public class ResponseResult {

	/**
	 * <pre>
	 * http 상태 코드.
	 * HttpServletResponse의 상수사용
	 * =============================
	 * 200 - OK
	 * 400 - Bad Request
	 * 500 - Internal Server Error
	 * 201 - Created
	 * 304 - Not Modified
	 * 404 - Not Found
	 * 401 - Unauthorized
	 * 403 - Forbidden
	 * =============================
	 * </pre>
	 *
	 * <code>
	 * Response.setStatus(HttpServletResponse.SC_OK)
	 * </code>
	 */
	private int status;

	/**
	 * 설명.
	 */
	private String desc;

	/**
	 * 추가 정보.
	 */
	private Object etc;

	public ResponseResult(int status, String desc, Object etc) {
		this.status	= status;
		this.desc	= desc;
		this.etc	= etc;
	}

	public ResponseResult(int status, Object etc) {
		this.status	= status;
		this.etc	= etc;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Object getEtc() {
		return etc;
	}

	public void setEtc(Object etc) {
		this.etc = etc;
	}
}
