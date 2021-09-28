package com.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.model.ProxyUser;

import brave.ScopedSpan;
import brave.Tracer;

@Component
public class Util_ProxyServer {

	private final Logger log = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private Util_OauthToken oauth_token;
		
	@Autowired
	Tracer tracer;
	
	@Value("${resourceService.createUserResourceUrl}")
	private String createUserResourceUrl;
	
	public String sendNewUserId(String id)  {
		
		ScopedSpan newSpan = tracer.startScopedSpan("sendNewUserId");		
		String httpResponse = null;
		try {		
			var response = restTemplate.postForEntity( getCreateUserResourceUrl(), httpIdRequestEntity(id), String.class);	
			newSpan.tag("Authentication-service ProxyServer sendNewUserId():", "User sendNewUserId");
			newSpan.annotate("sendNewUserId finished");
			newSpan.finish();
			log.debug("Resource Server Status = "+response.getStatusCode().toString()+ " with Id " + response.getBody());
		
			httpResponse = response.getStatusCode().toString();
		} catch (Exception e) {
			log.debug("Resource Server Not Found " + e.getMessage());
			httpResponse = "Resource Server Not Found " + e.getMessage();
		}	
		return httpResponse;
	}

		public HttpHeaders  createHeader(String accessToken) {	
				HttpHeaders header = new HttpHeaders();
				header.setContentType(MediaType.APPLICATION_JSON);
				header.set("Authorization", "Bearer "+ accessToken);			
					return header;
		}
		
		public HttpEntity<ProxyUser> httpIdRequestEntity(String id) {		
			var body = new ProxyUser();
			body.setId(id);		
				return new HttpEntity<>(body,createHeader(oauth_token.generateOAuth2AccessToken()));			
		}

		public String getCreateUserResourceUrl() {
			return createUserResourceUrl;
		}
}