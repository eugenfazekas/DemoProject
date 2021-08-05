package com.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.model.ProxyUser;
import com.model.User;
import com.repository.UserRepository;
import com.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass()); 
	private UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void createCollectionUsers() {
		userRepository.createCollectionUsers();
	}

	@Override
	public void dropCollectionUsers() {	
		userRepository.dropCollectionUsers();
	}

	@Override
	public void createUser(ProxyUser user) {
		//USER.Id  UNIQUE FIELD !!!
		User newUser = new User();
		newUser.setId(user.getId());
		newUser.setDate_registered(getDate());
		userRepository.createUser(newUser);
		createUserDirPath(newUser.getId());
		log.debug("New User with id "+ newUser.toString());
	}

	@Override
	public String getDate() {
		
		String pattern = "yyyy.MM.dd HH:mm:ss";
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	    
		return simpleDateFormat.format(new Date());
	}
	
	@Override
	public String getUsernameFromSecurityContext() {
		SecurityContext context = SecurityContextHolder.getContext();
    	Authentication a = context.getAuthentication();
		return a.getName();
	}

	@Override
	public void createUserDirPath(String userId) {
		    
	    Path finaelPath = Paths.get("src/main/resources/static/user/"+ userId);
	    try {
			Files.createDirectories(finaelPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    log.debug("Directory is created!");		
	}
	
	private User findById(String id) {
		return userRepository.findUserById(id);
	}
	
	@Override
	public User getUser() {
		return findById(getUsernameFromSecurityContext());
	}

	@Override
	public User updateUser(User user) {	
		
	  User userForUpdate = findById(getUsernameFromSecurityContext());  
		   userForUpdate.setFirstName(user.getFirstName());
		   userForUpdate.setLastName(user.getLastName());
		   userForUpdate.setFullName();
		   userForUpdate.setAddress(user.getAddress());
		   log.debug(userForUpdate.toString());
		return userRepository.updateUser(userForUpdate);
	}

	@Override
	public void uploadProfilePhoto(MultipartFile file) {
		
		String title = file.getOriginalFilename();
		String[] userIdInput = title.split("Ω");
		String userId = userRepository.findUserById(userIdInput[0]).getId();
		
		    try { 
		    	InputStream bis = file.getInputStream();
			    BufferedImage bImage2 = ImageIO.read(bis);
				ImageIO.write(bImage2, "png", new File("src/main/resources/static/user/" + userId + "/" + title+ ".png") );
				userRepository.uploadProfilePhoto(userId, title);
			} catch (IOException e) {
			log.debug("Erorr on saving imgae"+e.toString());
				e.printStackTrace();
			}
		
	}

	@Override
	public String deleteProfilePhoto(String imageName, boolean imageNameActive) {

		userRepository.deleteProfilePhoto(getUsernameFromSecurityContext(), imageName);
		
		if(imageNameActive == true) {
			userRepository.setActiveProfilePhoto(getUsernameFromSecurityContext(), "");
		}
		
		String response = null;
		 File myObj = new File("src/main/resources/static/user/" + getUsernameFromSecurityContext() + "/" + imageName + ".png"); 
		    if (myObj.delete()) { 
		    	response = "Deleted the file: " + myObj.getName();
		    } else {
		    	response = "Failed to delete the file.";
		    }
		return response;
	}
	
	@Override
	public void setActiveProfilePhoto( String photoName) {
		
		userRepository.setActiveProfilePhoto(getUsernameFromSecurityContext(), photoName);
	}

	
}
