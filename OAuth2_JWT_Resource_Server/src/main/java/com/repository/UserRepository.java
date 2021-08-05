package com.repository;

import com.model.User;

public interface UserRepository {

	public void createCollectionUsers();
	
	public void dropCollectionUsers();
	
	public void createUser(User user);
			
	public void addArticle(String userFullName, String article);
	
	public void deleteArticle(String userFullName, String article);
	
	public User findUserById(String id);	

	public User updateUser(User user);
	
	public void uploadProfilePhoto(String userId, String photoName);
	
	public void deleteProfilePhoto(String userId, String photoName);
	
	public void setActiveProfilePhoto(String userId, String photoName);
}
