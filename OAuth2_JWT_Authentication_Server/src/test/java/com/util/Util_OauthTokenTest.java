package com.util;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Util_OauthTokenTest {
	
	@Autowired
	private Util_OauthToken oauthToken;
	
	@Test
	@DisplayName("Testing Authentication_Service Util_OauthTokenTest generateOAuth2AccessToken function")
	void generateOAuth2AccessTokenTest() {
		
	String token = 	oauthToken.generateOAuth2AccessToken();	
	assertEquals(true, token.startsWith("eyJhb"));
	}
}





	
