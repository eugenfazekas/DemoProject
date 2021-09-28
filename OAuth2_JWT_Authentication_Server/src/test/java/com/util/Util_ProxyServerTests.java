package com.util;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.model.ProxyUser;

@SpringBootTest
public class Util_ProxyServerTests {
	
	@Autowired
	private Util_ProxyServer proxyServer;
	
	@MockBean
	private RestTemplate restTemplate;
	
	@MockBean 
	private Util_OauthToken oauth_token;

	  
	@Test
	void sendNewUserIdTest1() {
		
		String uuid =  UUID.randomUUID().toString();

		String url = proxyServer.getCreateUserResourceUrl();
		
		HttpEntity<ProxyUser> httpEntity = proxyServer.httpIdRequestEntity(uuid);
			
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(uuid,HttpStatus.OK);	

	    when(restTemplate.postForEntity(url, httpEntity, String.class)).thenReturn(responseEntity);

	    assertEquals("200 OK", proxyServer.sendNewUserId(uuid));		
	} 
	
	@Test
	void sendNewUserIdTest2() {
		
		String uuid =  UUID.randomUUID().toString();

		String url = proxyServer.getCreateUserResourceUrl();
		
		HttpEntity<ProxyUser> httpEntity = proxyServer.httpIdRequestEntity(uuid);

	    when(restTemplate.postForEntity(url, httpEntity, String.class)).thenThrow(IllegalStateException.class);

	    assertEquals("Resource Server Not Found "+null, proxyServer.sendNewUserId(uuid));		
	} 
	
	@Test
	void createHeaderTest() {
		
		HttpHeaders header = proxyServer.createHeader("token");
		String contentType = header.getContentType().toString();
		String authorization = header.get("Authorization").get(0);
		assertAll(		
	    		 () -> assertEquals(MediaType.APPLICATION_JSON.toString() ,contentType),
	    		 () -> assertEquals("Bearer "+ "token", authorization)  		 
	    		);
	}
	
	@Test
	void httpIdRequestEntityTest() {
		
		String id = "id";
		String token = "token";
		
		var body = new ProxyUser();
		body.setId(id);	
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.set("Authorization", "Bearer "+ token);	
		
		HttpEntity<ProxyUser> http =  new HttpEntity<>(body,header);
			
		when(oauth_token.generateOAuth2AccessToken()).thenReturn(token);
		
		assertEquals(http,  proxyServer.httpIdRequestEntity("id"));
	}
	
	@Test
	void  getCreateUserResourceUrlTest() {
		assertEquals("http://resource-service/api2/v1/user/createUserResource",  proxyServer.getCreateUserResourceUrl());
	}
}
