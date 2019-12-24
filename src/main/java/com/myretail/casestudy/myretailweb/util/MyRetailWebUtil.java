package com.myretail.casestudy.myretailweb.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class MyRetailWebUtil {
	
	public static HttpEntity<?> createJSONRequestEntity() {
		HttpHeaders requestHeaders = new HttpHeaders();    
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
		
		return requestEntity;
	}

}
