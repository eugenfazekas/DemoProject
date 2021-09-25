package com.integrationtest.UserControllerTests;


import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import com.exception.DuplicateUserException;
import com.exception.NoUsernameOrPasswordException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.User;
import com.model.UserUpdate;
import com.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTests {
	
	@Autowired
    private MockMvc mvc;
	 
	private User user; 
	
	@Autowired
	private UserService uservice;
	
	@Test
	public void registerUserIntegrationTest1() throws Exception {
		user = new User();
		user.setEmail("skybolt83@gmail.com");
		user.setPassword("myPassword");
		user.setMfa(false);
		
		mvc.perform(post("/api1/v1/user/registerUser")
	                .content(new ObjectMapper().writeValueAsString(user))
	                .contentType(MediaType.APPLICATION_JSON_VALUE))
	                .andExpect(status().isOk())
		            .andExpect(content().string(user.getEmail()));
	}
	
	

	
	@Test
	public void registerUserIntegrationTest2() throws Exception {
				
		user = new User();
		user.setEmail("");
		user.setPassword("myPassword");
		user.setMfa(false);
		
		try {
		mvc.perform(post("/api1/v1/user/registerUser")
	                .content(new ObjectMapper().writeValueAsString(user))
	                .contentType(MediaType.APPLICATION_JSON_VALUE))
		            .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoUsernameOrPasswordException))
		            .andExpect(result -> assertEquals("bla", result.getResolvedException().getMessage()));
		}catch(NestedServletException e) {
			assertEquals(NoUsernameOrPasswordException.class, e.getCause().getClass());
			assertEquals("Authentication_Server.UserService.registerUser --> email or password cannot be null or empty string!",e.getCause().getMessage());
		}
	}
	
	
	@Test
	public void registerUserIntegrationTest3() throws Exception {
				
		user = new User();
		user.setEmail("eu@fa.hu");
		user.setPassword("myPassword");
		user.setMfa(false);
		
		try {
		mvc.perform(post("/api1/v1/user/registerUser")
	                .content(new ObjectMapper().writeValueAsString(user))
	                .contentType(MediaType.APPLICATION_JSON_VALUE))
		            .andExpect(result -> assertTrue(result.getResolvedException() instanceof DuplicateUserException))
		            .andExpect(result -> assertEquals("bla", result.getResolvedException().getMessage()));
		}catch(NestedServletException e) {
			assertEquals(DuplicateUserException.class, e.getCause().getClass());
			assertEquals("Authentication_Server.UserService.registerUser --> User with this username allready exist!",e.getCause().getMessage());
		}
	}
	
	@Test	
	public void userExistCheckIntegrationTest1() throws Exception {
	
	String email = "eu@fa.hu";

	mvc.perform(post("/api1/v1/user/userExistCheck")
            .param("email", email))
            .andExpect(status().isOk())
            .andExpect(content().string("true"));
	}
	
	@Test	
	public void userExistCheckIntegrationTest2() throws Exception {
	
	String email = "eu@fa2.hu";	
	mvc.perform(post("/api1/v1/user/userExistCheck")
            .param("email", email))
            .andExpect(status().isOk())
            .andExpect(content().string("false"));
	}

	@Test
    public void codeCheckUserIntegrationTest() throws Exception {
		
		String code = "key";
		String url = "http://localhost:4200";

		mvc.perform(get("/api1/v1/user/userKeyCheck/"+code))
	            .andExpect(redirectedUrl(url));
 }
	 
	@Test
	public void updateUserAccountIntegrationTest() throws Exception{
		
		UserUpdate userUpdate = new UserUpdate();
		userUpdate.setId("884380f6-70cc-49f1-9135-532c3be6adde");
		userUpdate.setEmail("updated@email.hu");
		userUpdate.setOldPassword("myPassword2");
		userUpdate.setPassword("jelszo");
		String updateSuccess = "User have been updated!";
		
		mvc.perform(post("/api1/v1/user/updateUserAccount")
	                .content(new ObjectMapper().writeValueAsString(userUpdate))
	                .contentType(MediaType.APPLICATION_JSON_VALUE))
	                .andExpect(status().isOk())
		            .andExpect(content().string(updateSuccess));	
	}
	
	
	@Test
    public void createOneTimePasswordIntegrationTest() throws Exception {
		
		String otpCreated = "One time password send";
				
		mvc.perform(post("/api1/v1/user/createOneTimePassword")
                .header("username", "admin@fa.hu")
                .header("password", "myAdmin"))
                .andExpect(status().isOk())
	            .andExpect(content().string(otpCreated));

    }

	@Test
    public void mfaCheckIntegrationTest1() throws Exception {		
		String userExist = "true";
		
		mvc.perform(post("/api1/v1/user/mfaCheck")
                .header("username", "admin@fa.hu"))
                .andExpect(status().isOk())
	            .andExpect(content().string(userExist));
    }	
	
	@Test
    public void mfaCheckIntegrationTest2() throws Exception {		
		String userExist = "false";
		
		mvc.perform(post("/api1/v1/user/mfaCheck")
                .header("username", "eu@fa.hu"))
                .andExpect(status().isOk())
	            .andExpect(content().string(userExist));
    }
	
	@Test
    public void mfaCheckIntegrationTest3() throws Exception {		
		String userExist = "User Not Exist!";
		
		mvc.perform(post("/api1/v1/user/mfaCheck")
                .header("username", "eu2@fa.hu"))
                .andExpect(status().isOk())
	            .andExpect(content().string(userExist));
    }

}
