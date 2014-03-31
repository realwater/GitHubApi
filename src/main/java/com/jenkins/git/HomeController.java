package com.jenkins.git;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	public String login(Locale locale, Model model) {

		return "login";
	}

	@RequestMapping(value = "/opener.do", method = RequestMethod.GET)
	public void oppener(HttpServletResponse response) throws IOException {

		StringBuffer stb = new StringBuffer();
		stb.append("client_id=046fbf1a5040af820ffd");
		stb.append("&redirect_uri=http://git-local.test.com/callback.do");
		response.sendRedirect("https://github.com/login/oauth/authorize?"+stb.toString());
	}

	@RequestMapping(value = "/callback.do", method = RequestMethod.GET)
	public String callback(HttpServletRequest request, Model model) throws IOException {
		String clientId		= "046fbf1a5040af820ffd";
		String clientSecret = "6af73b5759d45956b58afdb9e2a86e8bbaea28d1";
		String code = request.getParameter("code");

//		StringBuffer stb = new StringBuffer();
//		stb.append("client_id="+clientId);
//		stb.append("&client_secret="+clientSecret);
//		stb.append("&code="+code);

		URL url = new URL("https://github.com/login/oauth/access_token");
        Map<String,Object> params = new LinkedHashMap<String, Object>();
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("code", code);

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection httpUrlConnection = (HttpURLConnection)url.openConnection();
        httpUrlConnection.setRequestMethod("POST");
        httpUrlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpUrlConnection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        httpUrlConnection.setDoOutput(true);
        httpUrlConnection.getOutputStream().write(postDataBytes);

        BufferedReader in = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream(), "UTF-8"));

        String result = "";
		String resultBuffer = "";

		while((resultBuffer = in.readLine()) != null) {
			result += resultBuffer;
		}

		model.addAttribute("result", result);
		return "result";
	}

	@RequestMapping(value = "/process.do", method = RequestMethod.GET)
	public String process(HttpServletRequest request, Model model) throws IOException {
		String token = "89940784dcb430cf5197819135249f7c3ab036ea";

        HttpURLConnection httpUrlConnection = null;
		BufferedReader bufferedReader = null;

	    URL targetUrl = new URL("https://api.github.com/user?access_token="+token);
		httpUrlConnection = (HttpURLConnection)targetUrl.openConnection();

		httpUrlConnection.setRequestMethod("GET");
		httpUrlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		httpUrlConnection.setConnectTimeout(10000);
		httpUrlConnection.setReadTimeout(10000);

		bufferedReader = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream(), "UTF-8"));

		String result = "";
		String resultBuffer = "";

		while((resultBuffer = bufferedReader.readLine()) != null) {
			result += resultBuffer;
		}

		model.addAttribute("result", result);
		return "result";
	}

	@RequestMapping(value = "/repo.do", method = RequestMethod.GET)
	public String repo(HttpServletRequest request, Model model) throws IOException {
        HttpURLConnection httpUrlConnection = null;
		BufferedReader bufferedReader = null;

	    URL targetUrl = new URL("https://api.github.com/users/realwater/repos");
		httpUrlConnection = (HttpURLConnection)targetUrl.openConnection();

		httpUrlConnection.setRequestMethod("GET");
		httpUrlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		httpUrlConnection.setConnectTimeout(10000);
		httpUrlConnection.setReadTimeout(10000);

		bufferedReader = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream(), "UTF-8"));

		String result = "";
		String resultBuffer = "";

		while((resultBuffer = bufferedReader.readLine()) != null) {
			result += resultBuffer;
		}

		model.addAttribute("result", result);
		return "result";
	}
	
	@RequestMapping(value = "/auth.do", method = RequestMethod.GET)
	public String auth(HttpServletRequest request, Model model) throws IOException {
        HttpURLConnection httpUrlConnection = null;
		BufferedReader bufferedReader = null;

	    URL targetUrl = new URL("https://api.github.com/user/repos");
		httpUrlConnection = (HttpURLConnection)targetUrl.openConnection();

		httpUrlConnection.setRequestProperty("Authorization", "token 89940784dcb430cf5197819135249f7c3ab036ea");
		httpUrlConnection.setRequestMethod("GET");
		httpUrlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		httpUrlConnection.setConnectTimeout(10000);
		httpUrlConnection.setReadTimeout(10000);

		bufferedReader = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream(), "UTF-8"));

		String result = "";
		String resultBuffer = "";

		while((resultBuffer = bufferedReader.readLine()) != null) {
			result += resultBuffer;
		}

		model.addAttribute("result", result);
		return "result";
	}


}
