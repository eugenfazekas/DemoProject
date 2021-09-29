package com.repository.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.model.User;
import com.repository.UserRepository;
import com.util.Util_JdbcTemplateUser;

@Repository
public class UserRepositoryImpl implements UserRepository {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
		
	private Util_JdbcTemplateUser jdbcTemplateUser;

	public UserRepositoryImpl(Util_JdbcTemplateUser jdbcTemplateUser) {
		this.jdbcTemplateUser = jdbcTemplateUser;
	}

	@Override
	public String createUsersTable() {
		
		String repositoryResponse = null;
			
		try {
			repositoryResponse = jdbcTemplateUser.createUsersTable();
			log.debug("Users Table Created");
			
		} catch (DataAccessException e) {
			log.debug("Users Table has not been created");
		}
		
		return repositoryResponse;	
	}

	@Override
	public String dropUsersTable() {
		
		String repositoryResponse = null;

		try {
			repositoryResponse = jdbcTemplateUser.dropUsersTable();
			log.debug("Users Table Deleted");
		
		} catch (DataAccessException e) {
			log.debug("Users Table has not been dropped");
		}
		
		return repositoryResponse;
	}

	@Override
	public User findById(String id) {
		
		User user = null;
		
		try {
			user = jdbcTemplateUser.findById(id);
			log.debug("UserRepository findById User found with id "+ id);
			
		} catch (DataAccessException e) {
			log.debug("Fullname: "+ id +" Not Fonud!");
		}
		
		return user;
	}
	
	@Override
	public User findByEmail(String email) {
		
		User user = null;
		
		try {
			user = jdbcTemplateUser.findByEmail(email);
			log.debug("UserRepository findByEmail user with email found "+user.toString());
		} catch (DataAccessException e) {
			log.debug("User with email: "+ email +" Not Fonud!");
		}
		
		return user;
	}

	@Override
	public String registerUser(User user,String authorities) {
	
		String repositoryResponse = null;
		
		try {
			Integer jdbcResponse = jdbcTemplateUser.registerUser(user, authorities);
			if(jdbcResponse == 1) { 
				repositoryResponse = "User Registered";
				log.debug("UserRepository registerUser New User registered "+user.toString());
				}
		} catch (DataAccessException e) {
			log.debug("UserRepository registerUser New User not registered "+user.toString());
		}
		
		return repositoryResponse;
	}

	@Override
	public Integer userExistCheck(String email) {

		Integer repositoryResponse = null;
		
		try {
				repositoryResponse = jdbcTemplateUser.userExistCheck(email);
				if(repositoryResponse == 1) { log.debug("UserRepository userExistCheck found "+ email); }
		} catch (DataAccessException e)  {
			log.debug("UserRepository userExistCheck user not found "+ email);
		}
		
		return repositoryResponse;
	}

	@Override
	public String setActiveUser(String email) {
	
		String activated = "User have not been Activated!";			
		Integer userExist = userExistCheck(email);
		
		if(userExist == 1) {
				try { 
					Integer jdbcResponse = jdbcTemplateUser.setActiveUser(email);
					if(jdbcResponse == 1) {
						activated = "User have been Activated!" ;
						log.debug("UserRepository setActiveUser User have been Activated! "+ email);
						}
				} catch (DataAccessException e)  {
					log.debug("UserRepository setActiveUser User has not been Activated! "+ email);
				}
		}
		return activated;
	}

	@Override
	public String updateUser(User user) {
		
		String update = "User not updated!";
		
		User userExist = null;
		userExist = findById(user.getId());
		if(userExist != null) {			
			try { 
				Integer jdbcResponse = jdbcTemplateUser.updateUser(userExist);
				if(jdbcResponse == 1) {
					update = "User have been updated!";
					log.debug("UserRepository updateUser User have been Updated"+user.toString());
				}
			} catch (DataAccessException e)  {
					log.debug("UserRepository updateUser User has not been Updated"+user.toString());
			}
		}		
			  return update;
	}
}
