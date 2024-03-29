package com.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.model.User;
import com.model.UserUpdate;
import com.service.OneTimePasswordService;
import com.service.UserService;

@RestController
@RequestMapping("api1/v1/user")
public class UserController {

	private UserService userService;
	private OneTimePasswordService oneTimePasswordService;
	
	public UserController(UserService userService, OneTimePasswordService oneTimePasswordService) {
		this.userService = userService;
		this.oneTimePasswordService = oneTimePasswordService;
	}

	@RequestMapping(value = "/registerUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String registerUser(@RequestBody User user) {
		userService.registerUser(user);
	    return user.getEmail();
	}
	
	@RequestMapping(value = "/userExistCheck", method = RequestMethod.POST)
	public boolean userExistCheck(@RequestParam String email) {
		return userService.userExistCheck(email);
	}
	
	@RequestMapping(path = "/userKeyCheck/{code}", method = RequestMethod.GET)
    public RedirectView codeCheckUser(@PathVariable("code") String code, HttpServletResponse response) {
		
		return userService.userActivation(code);

 }
	@RequestMapping(value = "/updateUserAccount", method = RequestMethod.POST)
	public String updateUserAccount(@RequestBody UserUpdate user) {
		return userService.updateUser(user);
	}
	
	@RequestMapping(path = "/createOneTimePassword", method = RequestMethod.POST)
    public String createOneTimePassword(@RequestHeader("username") String username, @RequestHeader("password") String password) {
		return oneTimePasswordService.createOneTimePassword(username, password);
    }
	
	@RequestMapping(path = "/mfaCheck", method = RequestMethod.POST)
    public String mfaCheck(@RequestHeader("username") String username) {
		return userService.mfaCheck(username);
    }
}
