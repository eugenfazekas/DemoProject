package com.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.User;
import com.model.UserUpdate;
import com.service.OneTimePasswordService;
import com.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {
	
	
	@MockBean
	private UserService mockUserService;
	
	@MockBean
	private OneTimePasswordService oneTimePasswordService;
	
	@Autowired
    private MockMvc mvc;
	 
	private User user; 
	
	@Test
	@DisplayName("Testing Authentication_Service UserController registerUser function endpoint and UserService.registerUser function if called with valid user")
	public void registerUserTest() throws Exception {
		user = new User();
		user.setEmail("eu@fa.hu");
		user.setPassword("myPassword2");
		user.setMfa(false);

		when(mockUserService.registerUser(user)).thenReturn(user.getEmail());
		
		mvc.perform(post("/api1/v1/user/registerUser")
	                .content(new ObjectMapper().writeValueAsString(user))
	                .contentType(MediaType.APPLICATION_JSON_VALUE))
	                .andExpect(status().isOk())
		            .andExpect(content().string(user.getEmail()));
 
		 verify(mockUserService, times(1)).registerUser(user);
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserController userExistCheck function endpoint and UserService.userExistCheck function if called with a valid username")
	public void userExistCheckTest() throws Exception {
	
	String email = "eu@fa.hu";
	when(mockUserService.userExistCheck(email)).thenReturn(true);
	
	mvc.perform(post("/api1/v1/user/userExistCheck")
            .param("email", email))
            .andExpect(status().isOk())
            .andExpect(content().string("true"));

	verify(mockUserService, times(1)).userExistCheck(email);
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserController userActivation function endpoint and UserService.userActivation function if called with a valid code")
    public void codeCheckUserTest() throws Exception {
		
		String code = "code";
		String url = "http://localhost:4200";
		
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl(url);
		 
		when(mockUserService.userActivation(code)).thenReturn(redirectView);
	
		mvc.perform(get("/api1/v1/user/userKeyCheck/"+code))
	            .andExpect(redirectedUrl(url));

		verify(mockUserService, times(1)).userActivation(code);
	}
	  
	@Test
	@DisplayName("Testing Authentication_Service UserController updateUserAccount function endpoint and UserService.updateUser function if called with a valid user")
	public void updateUserAccountTest() throws Exception{
		
		UserUpdate userUpdate = new UserUpdate();
		String updateSuccess = "User have been updated!";

		when(mockUserService.updateUser(userUpdate)).thenReturn(updateSuccess);
		
		mvc.perform(post("/api1/v1/user/updateUserAccount")
	                .content(new ObjectMapper().writeValueAsString(userUpdate))
	                .contentType(MediaType.APPLICATION_JSON_VALUE))
	                .andExpect(status().isOk())
		            .andExpect(content().string(updateSuccess));
 
		 verify(mockUserService, times(1)).updateUser(userUpdate);
	
	}
	
	@Test
	@DisplayName("Testing Authentication_Service UserController createOneTimePassword function endpoint and OneTimePasswordService.createOneTimePassword function if called with a valid user")
    public void createOneTimePasswordTest() throws Exception {
		
		String otpCreated = "One time password send";
		
		when(oneTimePasswordService.createOneTimePassword("eu@fa.hu", "pass")).thenReturn(otpCreated);
		
		mvc.perform(post("/api1/v1/user/createOneTimePassword")
                .header("username", "eu@fa.hu")
                .header("password", "pass"))
                .andExpect(status().isOk())
	            .andExpect(content().string(otpCreated));

	 verify(oneTimePasswordService, times(1)).createOneTimePassword("eu@fa.hu", "pass");

    }
	
	@Test
	@DisplayName("Testing Authentication_Service UserController mfaChec function endpoint and UserService.mfaCheck function if called with a valid user")
    public void mfaCheckTest() throws Exception {
		
		String userExist = "true";
		
		when(mockUserService.mfaCheck("eu@fa.hu")).thenReturn(userExist);
		
		mvc.perform(post("/api1/v1/user/mfaCheck")
                .header("username", "eu@fa.hu"))
                .andExpect(status().isOk())
	            .andExpect(content().string(userExist));

	 verify(mockUserService, times(1)).mfaCheck("eu@fa.hu");
    }

}
