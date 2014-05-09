package org.flyJenkins.controller;

import javax.servlet.http.HttpServletResponse;

import org.flyJenkins.controller.model.ResponseResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/errors")
public class ErrorController {

	@RequestMapping("/400")
	public void handle400(
			ModelMap model) {

		ResponseResult responseResult = new ResponseResult(
				HttpServletResponse.SC_BAD_REQUEST, "Request Error or Parameter Error");

		model.addAttribute(responseResult);
	}


	@RequestMapping("/404")
	public void handle404(
			ModelMap model) {

		ResponseResult responseResult = new ResponseResult(
				HttpServletResponse.SC_NOT_FOUND, "Not Found Resource");

		model.addAttribute(responseResult);
	}

	@RequestMapping("/500")
	public void handle500(
			ModelMap model) {

		ResponseResult responseResult = new ResponseResult(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500 Internal Server Error");

		model.addAttribute(responseResult);
	}

}