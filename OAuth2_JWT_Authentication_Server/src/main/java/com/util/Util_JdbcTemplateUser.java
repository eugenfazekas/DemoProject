package com.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.model.User;

@Component
public class Util_JdbcTemplateUser {

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
	
	public String createUsersTable () throws DataAccessException {

		jdbc.execute(Util_SqlQueryList.USER_CREATE_USERS_TABLE);

			return "Users Table Created!";	
	}

	public String dropUsersTable() throws DataAccessException {

		jdbc.execute(Util_SqlQueryList.USER_DROP_USERS_TABLE);

			return "Users Table Dropped!";
	}

	public User findById(String id) throws DataAccessException {

		return jdbc.queryForObject(Util_SqlQueryList.USER_FIND_BY_ID, mapper, id);		
	}
	
	public User findByEmail(String email) throws DataAccessException {
		
		return jdbc.queryForObject(Util_SqlQueryList.USER_FIND_BY_EMAIL, mapper, email);
	}

	public Integer registerUser(User user,String authorities) throws DataAccessException {
		
		return	jdbc.update(Util_SqlQueryList.USER_REGISTER_USER,user.getId(),user.getEmail(),user.getPassword(),user.isActive(),user.isMfa(),authorities);
	}

	public Integer userExistCheck(String email) throws DataAccessException {
		
		return  jdbc.queryForObject(Util_SqlQueryList.USER_USER_EXIST_CHECK, new Object[] {email}, Integer.class); 
	}

	public Integer setActiveUser(String email) throws DataAccessException {	
				
		Integer userExist = userExistCheck(email);
		
		if(userExist == 1) {
			return	jdbc.update(Util_SqlQueryList.USER_SET_ACTIVE_USER, email);
			}
				return null;
	}

	public Integer updateUser(User user) throws DataAccessException {

		User userExist = null;
		userExist = findById(user.getId());
		
		if(userExist != null) {			
				return jdbc.update(Util_SqlQueryList.USER_UPDATE_USER, user.getEmail(), user.getPassword(), user.isMfa(), user.getId() );
			}		
				return null;
	}	
}
