package com.integrationtest.UserControllerTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class OAuthTokenControllerTests {

	@Autowired
    private MockMvc mvc;
	
	 @Test
	 @DisplayName("Test authentication with Existing user with good credentials")
	 public void testAuthenticationWithExistingUserWithGoodCredentials() throws Exception {

		 mvc.perform(post("/oauth/token")
				 .param("grant_type", "password")
		 		 .param("username", "eu@fa.hu")
				 .param("password", "myPassword")
				 .param("scope", "read")
				 .header("Authorization", "Basic Y2xpZW50OnNlY3JldA=="))	
		 			.andExpect(status().isOk());
	    }
	 
	 @Test
	 @DisplayName("Test authentication with inexistent user")
	 public void testAuthenticationWithInexistentUser() throws Exception {

		 mvc.perform(post("/oauth/token")
				 .param("grant_type", "password")
		 		 .param("username", "john@fa.hu")
				 .param("password", "password")
				 .param("scope", "read")
				 .header("Authorization", "Basic Y2xpZW50OnNlY3JldA=="))
		 			.andExpect(status().isUnauthorized()).andReturn();
	    }
	 
	 @Test
	 @DisplayName("Test authentication with Existing user but with wrong password")
	 public void testAuthenticationWithExistingUserButWithWrongPassword() throws Exception {

		 mvc.perform(post("/oauth/token")
				 .param("grant_type", "password")
		 		 .param("username", "eu@fa.hu")
				 .param("password", "password")
				 .param("scope", "read")
				 .header("Authorization", "Basic Y2xpZW50OnNlY3JldA=="))
		 			.andExpect(status().isUnauthorized()).andReturn();
	    }
}
