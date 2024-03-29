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
import java.util.Optional;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.model.ProxyUser;
import com.model.User;
import com.repository.UserRedisRepository;
import com.repository.UserRepository;
import com.service.UserService;

import brave.ScopedSpan;
import brave.Tracer;

@Service
public class UserServiceImpl implements UserService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass()); 
	
	private UserRepository userRepository;
	private UserRedisRepository userRedisRepository;
	
	public UserServiceImpl(UserRepository userRepository, UserRedisRepository userRedisRepository) {
		this.userRepository = userRepository;
		this.userRedisRepository = userRedisRepository;
	}

	@Override
	public void createCollectionUsers() {
		userRepository.createCollectionUsers();
	}

	@Override
	public void dropCollectionUsers() {	
		userRepository.dropCollectionUsers();
	}

	@Autowired
	Tracer tracer;
	
	@Override
	public void createUser(ProxyUser user) {
		
		ScopedSpan newSpan = tracer.startScopedSpan("createUser");
		
		//USER.Id  UNIQUE FIELD !!!
		User newUser = new User();
		newUser.setId(user.getId());
		newUser.setDate_registered(getDate());
		userRepository.createUser(newUser);
		createUserDirPath(newUser.getId());
		log.debug("New User with id "+ newUser.toString());
		
		userRedisRepository.save(newUser);
		
		newSpan.tag("Resource-service UserServiceImpl createUser():", "User create User");
		newSpan.annotate("User createUser finished");
		newSpan.finish();
		log.debug("UserServiceImpl create User(): Inserted to redisRepository New User with id "+ newUser.toString());
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
			
		User user = new User();
		Optional<User> redisUser = userRedisRepository.findById(id) ;
		
			if(redisUser.isPresent()) {
				user = redisUser.get();
				ScopedSpan newSpan = tracer.startScopedSpan("findByIdRedis");	
				newSpan.tag("Resource-service UserServiceImpl findById with redis cache", "User findById Redis");
				newSpan.annotate("User findById redis finished");
				newSpan.finish();
				log.debug("UserServiceImpl findById(): Succesfully find and returnd redis user cache: " + id);			
				return user;

			} else {
				ScopedSpan newSpan = tracer.startScopedSpan("findByIdMongo");
				newSpan.tag("Resource-service UserServiceImpl findById from mongodb", "User findById Mongo");
				newSpan.annotate("User findById mongo finished");
				newSpan.finish();
				log.debug("UserServiceImpl findById(): Returnd User loaded from mongodb: " + id);
				return userRepository.findUserById(id);
		    }
			
			
	}
	
	@Override
	public User getUser() {
		
		log.debug("UserServiceImpl getUser(): ");
		return findById(getUsernameFromSecurityContext());
	}

	@Override
	public User updateUser(User user) {	
		
	  ScopedSpan newSpan = tracer.startScopedSpan("updateUser");	
		
	  User userForUpdate = findById(getUsernameFromSecurityContext());  
		   userForUpdate.setFirstName(user.getFirstName());
		   userForUpdate.setLastName(user.getLastName());
		   userForUpdate.setFullName();
		   userForUpdate.setAddress(user.getAddress());
		   log.debug(userForUpdate.toString());
		   
		   userRedisRepository.save(user);
		   
		   newSpan.tag("Resource-service UserServiceImpl update User", "User update User");
		   newSpan.annotate("User update User finished");
		   newSpan.finish();
		   log.debug("Redis User updated: "+userForUpdate.toString());
		   
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
			log.debug("Erorr on saving imgae "+e.toString());
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

	@Override
	public void prepareRedisUser(String id) {

			Optional<User> redisCheckedUser = userRedisRepository.findById(id);
			
			if(redisCheckedUser.isEmpty()) {
				ScopedSpan newSpan = tracer.startScopedSpan("prepareRedisUser");
				userRedisRepository.save(findById(id));
				newSpan.tag("Resource-service UserServiceImpl prepare RedisUser", "User prepare RedisUser");
				newSpan.annotate("User prepare RedisUser finished");
				newSpan.finish();
				log.debug("UserServiceImpl prepareRedisUser(): Redis User saved: " + id);
			}
	}

	@Override
	public void deleteRedisUser() {
				
		String id = getUsernameFromSecurityContext();
		Optional<User> redisCheckedUser = userRedisRepository.findById(id);
		ScopedSpan newSpan = tracer.startScopedSpan("deleteRedisUser");
		
		if(redisCheckedUser.isPresent()) {	
			
			userRedisRepository.deleteById(id);
			newSpan.tag("Resource-service UserServiceImpl delete RedisUser", "User delete RedisUser");
			newSpan.annotate("User deleteRedisUser finished");
			newSpan.finish();
			log.debug("UserServiceImpl deleteRedisUser(): Redis User Succesfully removed: " + id);
		}
				
	}
}
