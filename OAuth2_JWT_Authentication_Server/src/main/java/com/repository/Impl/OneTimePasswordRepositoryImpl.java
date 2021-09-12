package com.repository.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

		final String  sql = "CREATE TABLE IF NOT EXISTS ONETIMEPASSWORD (id VARCHAR(36) NOT NULL, email VARCHAR(64) PRIMARY KEY, UNIQUE (email), password VARCHAR(64) NOT NULL)";
		jdbc.execute(sql);  
		log.debug("ONETIMEPASSWORD Table Created");
		
		return "OneTimePassword Table Created";
	}

	@Override
	public String dropOneTimePasswordTable() {
		final String  sql = "DROP TABLE IF EXISTS ONETIMEPASSWORD";
		jdbc.execute(sql);	
		log.debug("ONETIMEPASSWORD Table Deleted");	
		
		return "OneTimePassword Table Deleted";
	}

	@Override
	public OneTimePassword createOneTimePassword(OneTimePassword oneTimePassword) {
		final String sql = "INSERT INTO ONETIMEPASSWORD (id,email,password) VALUES (?,?,?)";
		jdbc.update(sql,oneTimePassword.getId(),oneTimePassword.getEmail(),oneTimePassword.getPassword());
		log.debug("New ONETIMEPASSWORD:  " + oneTimePassword.toString());	
		return oneTimePassword;	
	}

	@Override
	public Integer OneTimePasswordCheck(String email) {
		final String  sql = "SELECT COUNT(*) FROM ONETIMEPASSWORD WHERE email = ?";
		int keysFound = jdbc.queryForObject(sql, new Object[] {email}, Integer.class); 
		if(keysFound > 0) { log.debug("ONETIMEPASSWORD Found "+ email); }
		return keysFound;
	}

	@Override
	public OneTimePassword findOneTimePassword(String email) {

		OneTimePassword oneTimePassword = null;
		final String  sql ="SELECT * FROM ONETIMEPASSWORD WHERE email = ?";
		try {
			oneTimePassword = jdbc.queryForObject(sql, mapper, email);
			log.debug("ONETIMEPASSWORD: "+email);
		}catch(EmptyResultDataAccessException e) {
			log.debug("ONETIMEPASSWORD: "+email+" Not Fonud!");
			}
			return oneTimePassword;
		}
	
	@Override
	public String removeOneTimePassword(String email) {		
		final String sql = "DELETE FROM ONETIMEPASSWORD WHERE email = ? ";
		jdbc.update(sql,email);
		log.debug("ONETIMEPASSWORD Deleted: "+email);
		
		return "OneTimePassword Deleted";
	}
}
