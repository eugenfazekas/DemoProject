package com.service;

import org.springframework.web.multipart.MultipartFile;

import com.model.ProxyUser;
import com.model.User;

public interface UserService {

	public void createCollectionUsers();
	
	public void dropCollectionUsers();
	
	public void createUser(ProxyUser user);
	
	public String getDate();
	
	public String getUsernameFromSecurityContext();
	
	public void createUserDirPath(String userId);
		
	public User getUser();

	public User updateUser(User user);	
	
	public void uploadProfilePhoto(MultipartFile fileInput);
	
	public String deleteProfilePhoto(String photoName, boolean imageNameActive);
	
	public void setActiveProfilePhoto(String photoName);
}
