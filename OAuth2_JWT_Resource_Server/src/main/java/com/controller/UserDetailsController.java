package com.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.model.User;
import com.service.UserService;

@RestController
@RequestMapping("userDetails")
public class UserDetailsController {
	
	private UserService userService;

	public UserDetailsController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "findUserByEmail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public User findUserByEmail(@RequestParam String email) {

		return null; //userService.findUserByEmail(email);
		
	}
	
	@RequestMapping(value = "updateUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public User updateUser(@RequestBody User user) {

		return userService.updateUser(user);
	}
	
	@RequestMapping(value = "saveProfileImage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String saveProfileImage(@RequestParam MultipartFile fileInput) {

		userService.uploadProfilePhoto(fileInput);
		return "Image saved";
	}
	
	@RequestMapping(value = "deleteProfileImage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteProfileImage(@RequestParam  String imageName, String imageNameActive) {
		
		userService.deleteProfilePhoto(imageName, Boolean.valueOf(imageNameActive));
		return "Image deleted";
	}
	
	@RequestMapping(value = "setActiveProfilePhoto", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String setActiveProfilePhoto(@RequestParam  String imageName) {

		 userService.setActiveProfilePhoto( imageName);
		 return imageName;

	}
}
