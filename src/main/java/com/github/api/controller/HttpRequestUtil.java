package com.github.api.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpRequestUtil {

	public static void send() throws IOException {
		HttpURLConnection httpUrlConnection = null;
		BufferedReader bufferedReader = null;

	    URL targetUrl = new URL("https://api.github.com/user/repos");
		httpUrlConnection = (HttpURLConnection)targetUrl.openConnection();

		httpUrlConnection.setRequestProperty("Authorization", "token 89940784dcb430cf5197819135249f7c3ab036ea");
		httpUrlConnection.setRequestMethod("GET");
		httpUrlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		httpUrlConnection.setConnectTimeout(3000);
		httpUrlConnection.setReadTimeout(3000);

		bufferedReader = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream(), "UTF-8"));

		String result = "";
		String resultBuffer = "";

		while((resultBuffer = bufferedReader.readLine()) != null) {
			result += resultBuffer;
		}
	}
}
