package com.integrationtest.UserControllerTests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.model.User;
import com.service.OneTimePasswordService;
import com.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTests {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OneTimePasswordService oneTimePasswordService;
	
	@Autowired
    private MockMvc mvc;
	 
	private User user; 
	
}
