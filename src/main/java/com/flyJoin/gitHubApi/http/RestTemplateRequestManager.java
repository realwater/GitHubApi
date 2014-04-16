package com.flyJoin.gitHubApi.http;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.flyJoin.gitHubApi.model.ResultInfo;


public class RestTemplateRequestManager {

	RestTemplate restTemplate;

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public ResultInfo getExchange (String url , HttpServletRequest request)  {
		ResultInfo sendDataResult = new ResultInfo();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Referer", url);
		headers.set("Content-Type", "application/x-www-form-urlencoded");
		HttpEntity<ModelMap> requestEntity = new HttpEntity<ModelMap>(headers);
		try {
			ResponseEntity<ResultInfo> responseEntity =restTemplate.exchange(url,HttpMethod.GET, requestEntity, ResultInfo.class);
			sendDataResult = responseEntity.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sendDataResult;
	}

	public ResultInfo postExchange (String url ,MultiValueMap<?, ?> params, HttpServletRequest request)  {
		ResultInfo sendDataResult = new ResultInfo();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Referer", url);
		HttpEntity<MultiValueMap<?, ?>> requestEntity = new HttpEntity<MultiValueMap<?, ?>>(params, headers);
		try {
			ResponseEntity<ResultInfo> responseEntity =restTemplate.exchange(url,HttpMethod.POST, requestEntity, ResultInfo.class);
			sendDataResult = responseEntity.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sendDataResult;
	}

}
