package com.repository.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
		final String  sql = "CREATE TABLE IF NOT EXISTS USERS (id VARCHAR(36) PRIMARY KEY, email VARCHAR(64) NOT NULL, UNIQUE  (email), password VARCHAR(64) NOT NULL, active BOOLEAN, mfa BOOLEAN, authorities VARCHAR(64) NOT NULL);";
		jdbc.execute(sql);
		log.debug("Users Table Created");
		
		return "Users Table Created!";	
	}

	@Override
	public String dropUsersTable() {
		final String  sql = "DROP TABLE IF EXISTS USERS";
		jdbc.execute(sql);	
		log.debug("Users Table Deleted");
		
		return "Users Table Dropped!";
	}

	@Override
	public User findById(String id) {
		
		User user = null;
		final String  sql ="SELECT * FROM users WHERE id = ?";
		try {
			user = jdbc.queryForObject(sql, mapper, id);
		}catch(EmptyResultDataAccessException e) {
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
			log.debug(user.toString());
		}catch(EmptyResultDataAccessException e) {
			log.debug("email: "+ email +" Not Fonud!");
			}
		return user;
	}

	@Override
	public String registerUser(User user,String authorities) {
	
		final String sql = "INSERT INTO USERS (id,email,password,active,mfa,authorities) VALUES (?,?,?,?,?,?)";
		jdbc.update(sql,user.getId(),user.getEmail(),user.getPassword(),user.isActive(),user.isMfa(),authorities);
		log.debug("New User registered "+user.toString());
		return "User Registered";
	}

	@Override
	public Integer userExistCheck(String email) {

		final String  sql = "SELECT COUNT(*)  FROM USERS WHERE email = ?";
		int user = jdbc.queryForObject(sql, new Object[] {email}, Integer.class); 
		
		return user;
	}

	@Override
	public String setActiveUser(String email) {
	
		String activated = "User have not been Activated!";		
		final String  sql ="UPDATE users SET active = true  where email = ? ";
		
		Integer userExist = userExistCheck(email);
		
		if(userExist == 1) {
					
			try { 
				jdbc.update(sql, email);
				activated = "User have been Activated!" ;
				log.debug("User have been Activated! "+ email);
			} catch (Exception e) {
				log.debug("User have not been Activated!"+ email);
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
					final String  sql ="UPDATE users SET email = ?, password = ?, mfa = ?  where id = ? ";
					jdbc.update(sql, user.getEmail(), user.getPassword(), user.isMfa(), user.getId() );
					update = "User have been updated!";
					log.debug("User have been Updated"+user.toString());
				} catch(Exception e) {
					log.debug("Update have not been executed "+ e);
				}
			}
		return update;
	}
}
