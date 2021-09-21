package com.util;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Util_OauthTokenTest {
	
	@Autowired
	private Util_OauthToken oauthToken;
	
	@Test
	void generateOAuth2AccessTokenTest() {
		
	String token = 	oauthToken.generateOAuth2AccessToken();	
	assertEquals(true, token.startsWith("eyJhb"));
	}
}





	
