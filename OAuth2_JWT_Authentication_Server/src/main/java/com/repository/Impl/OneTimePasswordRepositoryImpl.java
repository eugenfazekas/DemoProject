package com.repository.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.model.OneTimePassword;
import com.repository.OneTimePasswordRepository;

@Repository
public class OneTimePasswordRepositoryImpl implements OneTimePasswordRepository {
	
private final Logger log = LoggerFactory.getLogger(this.getClass());
	
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
	
	@Override
	public String createOneTimePasswordTable() {

		String repositoryResponse = null;
		final String  sql = "CREATE TABLE IF NOT EXISTS ONETIMEPASSWORD (id VARCHAR(36) NOT NULL, email VARCHAR(64) PRIMARY KEY, UNIQUE (email), password VARCHAR(64) NOT NULL)";
		
		try {
			jdbc.execute(sql); 
			repositoryResponse ="OneTimePassword Table Created";
			log.debug("ONETIMEPASSWORD Table Created");
			
		} catch (DataAccessException e) {
			log.debug("ONETIMEPASSWORD Table has not been created");
		}
		return repositoryResponse;
	}

	@Override
	public String dropOneTimePasswordTable() {
		
		String repositoryResponse = null;
		final String  sql = "DROP TABLE IF EXISTS ONETIMEPASSWORD";
		
		try {
			jdbc.execute(sql);	
			log.debug("ONETIMEPASSWORD Table Deleted");	
			repositoryResponse = "OneTimePassword Table Deleted";
			
		} catch (DataAccessException e) {
			log.debug("ONETIMEPASSWORD Table has not been deleted");
		}
		return repositoryResponse;
	}

	@Override
	public OneTimePassword createOneTimePassword(OneTimePassword oneTimePassword) {
		
		OneTimePassword repositoryResponse = null;
		final String sql = "INSERT INTO ONETIMEPASSWORD (id,email,password) VALUES (?,?,?)";
		
		try {
			jdbc.update(sql,oneTimePassword.getId(),oneTimePassword.getEmail(),oneTimePassword.getPassword());
			repositoryResponse = oneTimePassword;
			log.debug("New ONETIMEPASSWORD:  " + oneTimePassword.toString());	
			
		} catch (DataAccessException e) {
			log.debug("New ONETIMEPASSWORD has not been created: " + oneTimePassword.toString());
		}
		
		return repositoryResponse;	
	}

	@Override
	public Integer OneTimePasswordCheck(String email) {
	
		Integer repositoryResponse = null;
		final String  sql = "SELECT COUNT(*) FROM ONETIMEPASSWORD WHERE email = ?";
		
		try {
			 repositoryResponse = jdbc.queryForObject(sql, new Object[] {email}, Integer.class); 
			if(repositoryResponse > 0) { log.debug("ONETIMEPASSWORD Found "+ email); }
		
		} catch (DataAccessException e)  {
			   log.debug("ONETIMEPASSWORD has not found" +e);
		}
		
		return repositoryResponse;
	}

	@Override
	public OneTimePassword findOneTimePassword(String email) {

		OneTimePassword oneTimePassword = null;
		final String  sql ="SELECT * FROM ONETIMEPASSWORD WHERE email = ?";
		
		try {
			oneTimePassword = jdbc.queryForObject(sql, mapper, email);
			log.debug("ONETIMEPASSWORD: "+email);
		} catch (DataAccessException e)  {
			log.debug("ONETIMEPASSWORD: "+email+" Has Not Found!");
		}
		
			return oneTimePassword;
	}
	
	@Override
	public String removeOneTimePassword(String email) {	
		
		String repositoryResponse = null;
		final String sql = "DELETE FROM ONETIMEPASSWORD WHERE email = ?";
		
		try {
			jdbc.update(sql,email);
			repositoryResponse = "OneTimePassword Deleted";
			log.debug("ONETIMEPASSWORD Deleted: "+email);
		
		} catch (DataAccessException e)  {
			log.debug("ONETIMEPASSWORD has not been deleted: "+email);
		}
		
			return repositoryResponse;
	}
}
