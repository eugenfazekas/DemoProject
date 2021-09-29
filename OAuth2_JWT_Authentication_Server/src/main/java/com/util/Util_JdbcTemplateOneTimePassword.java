package com.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.model.OneTimePassword;

@Component
public class Util_JdbcTemplateOneTimePassword {

	@Autowired
	private JdbcTemplate jdbc;
	
	final RowMapper<OneTimePassword> mapper = new RowMapper<OneTimePassword>() {

		public OneTimePassword mapRow(ResultSet rs ,int rowNum) throws SQLException {
			
			OneTimePassword n = new OneTimePassword();
			n.setId(rs.getString("id"));
			n.setEmail(rs.getString("email"));
			n.setPassword(rs.getString("password"));
		
			return n;
		}
	};
	
	public String createOneTimePasswordTable() throws DataAccessException {

		jdbc.execute(Util_SqlQueryList.ONETIMEPASSWORD_CREATE_ONETIMEPASSWORDS_TABLE); 

			return "OneTimePassword Table Created";
	}

	public String dropOneTimePasswordTable() throws DataAccessException {

		jdbc.execute(Util_SqlQueryList.ONETIMEPASSWORD_DROP_ONETIMEPASSWORDS_TABLE);	

			return "OneTimePassword Table Deleted";
	}

	public OneTimePassword createOneTimePassword(OneTimePassword oneTimePassword) throws DataAccessException {

		jdbc.update(Util_SqlQueryList.ONETIMEPASSWORD_CREATE_ONE_TIME_PASSWORD,oneTimePassword.getId(),oneTimePassword.getEmail(),oneTimePassword.getPassword());
			
			return oneTimePassword;
	}

	public Integer OneTimePasswordCheck(String email) throws DataAccessException {

		return jdbc.queryForObject(Util_SqlQueryList.ONETIMEPASSWORD_ONE_TIME_PASSWORD_CHECK, new Object[] {email}, Integer.class); 
	}

	public OneTimePassword findOneTimePassword(String email) throws DataAccessException {

		return jdbc.queryForObject(Util_SqlQueryList.ONETIMEPASSWORD_FIND_ONE_TIME_PASSWORD, mapper, email);
	}
	
	public Integer removeOneTimePassword(String email) throws DataAccessException {	

			return jdbc.update(Util_SqlQueryList.ONETIMEPASSWORD_REMOVE_ONE_TIME_PASSWORD,email);
	}
}
