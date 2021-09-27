package com.repository.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.model.User;
import com.repository.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {
	
private final Logger log = LoggerFactory.getLogger(this.getClass());
		
	@Autowired
    private JdbcTemplate jdbc;
			
	final RowMapper<User> mapper = new RowMapper<User>() {

		public User mapRow(ResultSet rs ,int rowNum) throws SQLException {
			
			User n = new User();
			
			n.setId(rs.getString("id"));
			n.setEmail(rs.getString("email"));
			n.setPassword(rs.getString("password"));
			n.setActive(rs.getBoolean("active"));
			n.setMfa(rs.getBoolean("mfa"));
			n.setAuthorities(Arrays.asList(rs.getString("authorities").split(" ")));
					
			return n;
		}
	};
	
	@Override
	public String createUsersTable() {
		
		String repositoryResponse = null;
		final String  sql = "CREATE TABLE IF NOT EXISTS USERS (id VARCHAR(36) PRIMARY KEY, email VARCHAR(64) NOT NULL, UNIQUE  (email), password VARCHAR(64) NOT NULL, active BOOLEAN, mfa BOOLEAN, authorities VARCHAR(64) NOT NULL);";
		
		try {
			jdbc.execute(sql);
			repositoryResponse = "Users Table Created!";
			log.debug("Users Table Created");
			
		} catch (DataAccessException e) {
			log.debug("Users Table has not been created");
		}
		return repositoryResponse;	
	}

	@Override
	public String dropUsersTable() {
		String repositoryResponse = null;
		final String  sql = "DROP TABLE IF EXISTS USERS";
		
		try {
			jdbc.execute(sql);
			repositoryResponse = "Users Table Dropped!";
			log.debug("Users Table Deleted");
		
		} catch (DataAccessException e) {
			log.debug("Users Table has not been dropped");
		}
		
		return repositoryResponse;
	}

	@Override
	public User findById(String id) {
		
		User user = null;
		final String  sql ="SELECT * FROM users WHERE id = ?";
		
		try {
			user = jdbc.queryForObject(sql, mapper, id);
			log.debug("UserRepository findById User found with id "+ id);
			
		} catch (DataAccessException e) {
			log.debug("Fullname: "+ id +" Not Fonud!");
		}
		
		return user;
	}
	
	@Override
	public User findByEmail(String email) {
		
		User user = null;
		final String  sql ="SELECT * FROM users WHERE email = ?";
		
		try {
			user = jdbc.queryForObject(sql, mapper, email);
			log.debug("UserRepository findByEmail user with email found "+user.toString());
		} catch (DataAccessException e) {
			log.debug("User with email: "+ email +" Not Fonud!");
		}
		
		return user;
	}

	@Override
	public String registerUser(User user,String authorities) {
	
		String repositoryResponse = null;
		final String sql = "INSERT INTO USERS (id,email,password,active,mfa,authorities) VALUES (?,?,?,?,?,?)";
		
		try {
			jdbc.update(sql,user.getId(),user.getEmail(),user.getPassword(),user.isActive(),user.isMfa(),authorities);
			repositoryResponse = "User Registered";
			log.debug("UserRepository registerUser New User registered "+user.toString());
		
		} catch (DataAccessException e) {
			log.debug("UserRepository registerUser New User not registered "+user.toString());
		}
		
		return repositoryResponse;
	}

	@Override
	public Integer userExistCheck(String email) {

		Integer repositoryResponse = null;
		final String  sql = "SELECT COUNT(*)  FROM USERS WHERE email = ?";
		
		try {
			repositoryResponse = jdbc.queryForObject(sql, new Object[] {email}, Integer.class); 
			if(repositoryResponse > 0) { log.debug("UserRepository userExistCheck found "+ email); }
			
		} catch (DataAccessException e)  {
			log.debug("UserRepository userExistCheck user not found "+ email);
		}
		
		return repositoryResponse;
	}

	@Override
	public String setActiveUser(String email) {
	
		String activated = "User have not been Activated!";		
		final String  sql ="UPDATE users SET active = true  where email = ?";
		
		Integer userExist = userExistCheck(email);
		
		if(userExist == 1) {
				try { 
					jdbc.update(sql, email);
					activated = "User have been Activated!" ;
					log.debug("UserRepository setActiveUser User have been Activated! "+ email);
					
				} catch (DataAccessException e)  {
					log.debug("UserRepository setActiveUser User has not been Activated! "+ email);
				}
		}
		return activated;
	}

	@Override
	public String updateUser(User user) {
		
		String update = "User not updated!";
		final String  sql ="UPDATE users SET email = ?, password = ?, mfa = ?  where id = ?";
		User userExist = null;
		userExist = findById(user.getId());
		
		if(userExist != null) {			
			try { 
					jdbc.update(sql, user.getEmail(), user.getPassword(), user.isMfa(), user.getId() );
					update = "User have been updated!";
					log.debug("UserRepository updateUser User have been Updated"+user.toString());
			
			} catch (DataAccessException e)  {
					log.debug("UserRepository updateUser User has not been Updated"+user.toString());
			}
		}
		
		return update;
	}
}
