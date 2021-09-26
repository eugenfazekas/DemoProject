package com.integrationtest.UserControllerTests;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import com.exception.DuplicateUserException;
import com.exception.NoUserActivationKeyException;
import com.exception.NoUserIdException;
import com.exception.NoUsernameOrPasswordException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.User;
import com.model.UserUpdate;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTests {
	
	@Autowired
    private MockMvc mvc;
	 
	private User user; 
	
	@Test
	@DisplayName("Testing Authentication_Service UserControllerIntegrationTests registerUser function endpoint with valid user")
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
	@DisplayName("Testing Authentication_Service UserControllerIntegrationTests registerUser function endpoint with invalid username")
	public void registerUserIntegrationTest2() throws Exception {
				
		user = new User();
		user.setEmail("");
		user.setPassword("myPassword");
		user.setMfa(false);
		
		try {
		mvc.perform(post("/api1/v1/user/registerUser")
	                .content(new ObjectMapper().writeValueAsString(user))
	                .contentType(MediaType.APPLICATION_JSON_VALUE));
		}catch(NestedServletException e) {
			assertEquals(NoUsernameOrPasswordException.class, e.getCause().getClass());
			assertEquals("Authentication_Server.UserService.registerUser --> email or password cannot be null or empty string!",e.getCause().getMessage());
		}
	}
	
	
	@Test
	@DisplayName("Testing Authentication_Service UserControllerIntegrationTests registerUser function endpoint with allready registered username")
	public void registerUserIntegrationTest3() throws Exception {
				
		user = new User();
		user.setEmail("eu@fa.hu");
		user.setPassword("myPassword");
		user.setMfa(false);
		
		try {
		mvc.perform(post("/api1/v1/user/registerUser")
	                .content(new ObjectMapper().writeValueAsString(user))
	                .contentType(MediaType.APPLICATION_JSON_VALUE));
		}catch(NestedServletException e) {
			assertEquals(DuplicateUserException.class, e.getCause().getClass());
			assertEquals("Authentication_Server.UserService.registerUser --> User with this username allready exist!",e.getCause().getMessage());
		}
	}
	
	@Test	
	@DisplayName("Testing Authentication_Service UserControllerIntegrationTests userExistCheckIntegration function endpoint with registered username")
	public void userExistCheckIntegrationTest1() throws Exception {
	
	String email = "eu@fa.hu";

	mvc.perform(post("/api1/v1/user/userExistCheck")
            .param("email", email))
            .andExpect(status().isOk())
            .andExpect(content().string("true"));
	}
	
	@Test	
	@DisplayName("Testing Authentication_Service UserControllerIntegrationTests userExistCheckIntegration function endpoint with not registered username")
	public void userExistCheckIntegrationTest2() throws Exception {
	
	String email = "eu@fa2.hu";	
	mvc.perform(post("/api1/v1/user/userExistCheck")
            .param("email", email))
            .andExpect(status().isOk())
            .andExpect(content().string("false"));
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserControllerIntegrationTests userExistCheckIntegration function endpoint with empty username")
	public void userExistCheckIntegrationTest3() throws Exception {
	
	String email = "";	
	try {
		mvc.perform(post("/api1/v1/user/userExistCheck")
	            .param("email", email));
		}catch(NestedServletException e) {
			assertEquals(NoUsernameOrPasswordException.class, e.getCause().getClass());
			assertEquals("Authentication_Server.UserService.userExistCheck --> email cannot be null or empty string!",e.getCause().getMessage());
		}
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserControllerIntegrationTests userKeyCheckIntegration function endpoint with valid key")
    public void userKeyCheckIntegrationTest() throws Exception {
		
		String code = "key";
		String url = "http://localhost:4200";
		mvc.perform(get("/api1/v1/user/userKeyCheck/"+code))
	            .andExpect(redirectedUrl(url));
 }
	
	@Test
	@DisplayName("Testing Authentication_Service UserControllerIntegrationTests userKeyCheckIntegration function endpoint with empty key")
    public void userKeyCheckIntegrationTest2() throws Exception {
		
		String code = "";
		try {
			mvc.perform(get("/api1/v1/user/userKeyCheck/"+code));
			}catch(NestedServletException e) {
				assertEquals(NoUserActivationKeyException.class, e.getCause().getClass());
				assertEquals("Authentication_Server.UserService.userExistCheck --> email cannot be null or empty string!",e.getCause().getMessage());
			}
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserControllerIntegrationTests userKeyCheckIntegration function endpoint with invalid key")
    public void userKeyCheckIntegrationTes3() throws Exception {
		
		String code = "key2";
		mvc.perform(get("/api1/v1/user/userKeyCheck/"+code))
	            .andExpect(redirectedUrl(null));
 }
	 
	@Test
	@DisplayName("Testing Authentication_Service UserControllerIntegrationTests updateUserAccount function endpoint with valid user")
	public void updateUserAccountIntegrationTest1() throws Exception{
		
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
	@DisplayName("Testing Authentication_Service UserControllerIntegrationTests updateUserAccount function endpoint with invalid userId")
	public void updateUserAccountIntegrationTest2() throws Exception{
		
		UserUpdate userUpdate = new UserUpdate();
		userUpdate.setId("");
		userUpdate.setEmail("updated@email.hu");
		userUpdate.setOldPassword("myPassword2");
		userUpdate.setPassword("jelszo");
		
		try {
			mvc.perform(post("/api1/v1/user/updateUserAccount")
			.content(new ObjectMapper().writeValueAsString(userUpdate))
            .contentType(MediaType.APPLICATION_JSON_VALUE));
			}catch(NestedServletException e) {
				assertEquals(NoUserIdException.class, e.getCause().getClass());
				assertEquals("Authentication_Server.UserService.updateUser --> updateUser.id cannot be null or empty string!",e.getCause().getMessage());
			}
	}
	
	
	@Test
	@DisplayName("Testing Authentication_Service UserControllerIntegrationTests createOneTimePassword function endpoint with valid user")
    public void createOneTimePasswordIntegrationTest1() throws Exception {
		
		String otpCreated = "One time password send";
				
		mvc.perform(post("/api1/v1/user/createOneTimePassword")
                .header("username", "admin@fa.hu")
                .header("password", "myAdmin"))
                .andExpect(status().isOk())
	            .andExpect(content().string(otpCreated));

    }
	
	@Test
	@DisplayName("Testing Authentication_Service UserControllerIntegrationTests createOneTimePassword function endpoint with empty username")
    public void createOneTimePasswordIntegrationTest2() throws Exception {

		try {
			mvc.perform(post("/api1/v1/user/createOneTimePassword")
	                .header("username", "")
	                .header("password", "myAdmin"));
			}catch(NestedServletException e) {
				assertEquals(NoUsernameOrPasswordException.class, e.getCause().getClass());
				assertEquals("Authentication_Server.OneTimePasswordService.createOneTimePassword --> accountkey, email, usertype cannot be null!",e.getCause().getMessage());
			}
    }
	
	@Test
	@DisplayName("Testing Authentication_Service UserControllerIntegrationTests createOneTimePassword function endpoint with empty password")
    public void createOneTimePasswordIntegrationTest3() throws Exception {

		try {
			mvc.perform(post("/api1/v1/user/createOneTimePassword")
	                .header("username", "admin@fa.hu")
	                .header("password", ""));
			}catch(NestedServletException e) {
				assertEquals(NoUsernameOrPasswordException.class, e.getCause().getClass());
				assertEquals("Authentication_Server.OneTimePasswordService.createOneTimePassword --> accountkey, email, usertype cannot be null!",e.getCause().getMessage());
			}
    }

	@Test
	@DisplayName("Testing Authentication_Service UserControllerIntegrationTests mfaCheck function endpoint with valid username")
    public void mfaCheckIntegrationTest1() throws Exception {		
		String userExist = "true";
		
		mvc.perform(post("/api1/v1/user/mfaCheck")
                .header("username", "admin@fa.hu"))
                .andExpect(status().isOk())
	            .andExpect(content().string(userExist));
    }	
	
	@Test
	@DisplayName("Testing Authentication_Service UserControllerIntegrationTests mfaCheck function endpoint with user without mfa enabled")
    public void mfaCheckIntegrationTest2() throws Exception {		
		String userExist = "false";
		
		mvc.perform(post("/api1/v1/user/mfaCheck")
                .header("username", "eu@fa.hu"))
                .andExpect(status().isOk())
	            .andExpect(content().string(userExist));
    }
	
	@Test
	@DisplayName("Testing Authentication_Service UserControllerIntegrationTests mfaCheck function endpoint with not exist user!")
    public void mfaCheckIntegrationTest3() throws Exception {		
		String userExist = "User Not Exist!";
		
		mvc.perform(post("/api1/v1/user/mfaCheck")
                .header("username", "eu2@fa.hu"))
                .andExpect(status().isOk())
	            .andExpect(content().string(userExist));
    }
	
	@Test
	@DisplayName("Testing Authentication_Service UserControllerIntegrationTests mfaCheck function endpoint with empty username")
    public void mfaCheckIntegrationTest4() throws Exception {		
		
		try {
			mvc.perform(post("/api1/v1/user/mfaCheck")
	                .header("username", ""));
			}catch(NestedServletException e) {
				assertEquals(NoUsernameOrPasswordException.class, e.getCause().getClass());
				assertEquals("Authentication_Server.UserService.mfaCheck --> header email cannot be null or empty string!",e.getCause().getMessage());
			}
    }
}
