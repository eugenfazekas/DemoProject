package com.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
@Configuration
public class DataSourceConfig {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Value("${authentication_database.url}")
	private String url;
	
	@Value("${authentication_database.db}")
	private String db;
	
	@Value("${authentication_database.username}")
	private String username;
	
	@Value("${authentication_database.password}")
	private String password;
	
	public DataSource mySqlDataSource() {
		
		log.debug("url "+url +" db "+db+" username "+username);
		log.debug("fullUrl "+"jdbc:postgresql://" + url+ "/" + db);
	        DriverManagerDataSource dataSource = new DriverManagerDataSource();
	       
	        dataSource.setUrl("jdbc:postgresql://" + url+ "/" + db);
	        dataSource.setUsername(username);
	        dataSource.setPassword(password);
	        dataSource.setDriverClassName("org.postgresql.Driver");
	            
	        return dataSource;
	    }
	
	@Bean
	public JdbcTemplate jdbcTemplate() {
	    return new JdbcTemplate(mySqlDataSource());
	}
}
