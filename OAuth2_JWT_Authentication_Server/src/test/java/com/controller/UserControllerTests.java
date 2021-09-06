package com.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.User;
import com.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {
	
	
	@MockBean
	private UserService mockUserService;
	
	@Autowired
    private MockMvc mvc;
	 
	private User user; 
	
	@Test
	public void registerUser() throws Exception {
		user = new User();
		user.setEmail("eu@fa2.hu");
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
	public void userExistCheck() throws Exception {
	
	String email = "eu@fa.hu";
	when(mockUserService.userExistCheck(email)).thenReturn(true);
	
	mvc.perform(post("/api1/v1/user/userExistCheck")
            .param("email", email))
            .andExpect(status().isOk())
            .andExpect(content().string("true"));

	verify(mockUserService, times(1)).userExistCheck(email);
	}
	
	@Test
    public void codeCheckUser() throws Exception {
		
		String code = "code";
		String url = "http://localhost:4200";
		
		doNothing().when(mockUserService).userActivation(code);
	
		mvc.perform(get("/api1/v1/user/userKeyCheck/"+code))
	            .andExpect(redirectedUrl(url));

		verify(mockUserService, times(1)).userActivation(code);
 }
	/*
	  
	@Test
	public void updateUserAccount() {
		//return userService.updateUser(user);
	}
	
	@Test
    public void createOneTimePassword() {
		//return oneTimePasswordService.createOneTimePassword();
    }
	
	@Test
    public void mfaCheck() {
		//return userService.mfaCheck();
    }
*/
}
