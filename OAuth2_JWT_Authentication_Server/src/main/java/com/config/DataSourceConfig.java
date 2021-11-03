package com.config;

import java.util.Properties;

import javax.sql.DataSource;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
@Configuration
public class DataSourceConfig {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Value("${spring.datasource.url}")
	private String url;

	@Value("${spring.datasource.username}")
	private String username;
	
	@Value("${spring.datasource.password}")
	private String password;
	
	@Value("${spring.database.databaseName}")
	private String db ;

	public DataSource mySqlDataSource() {
		
		log.debug("Datasource Configuration: url "+url +" db "+db+" username "+username + " password "+ password) ;
		log.debug("DataSource Url "+"jdbc:postgresql://" + url+ "/" + db);
		
		log.debug("MailSending Configuration: mail.host " +host +" .mail.port "+port + " mail.username "+ mail_username + " mail.password " + mail_password) ;
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
	
	@Value("${spring.mail.host}")
	private String host;

	@Value("${spring.mail.port}")
	private int port;
	
	@Value("${spring.mail.username}")
	private String mail_username;
	
	@Value("${spring.mail.password}")
	private String mail_password;
	
	@Bean
	public JavaMailSender getJavaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost(host);
	    mailSender.setPort(port);
	    
	    mailSender.setUsername(mail_username);
	    mailSender.setPassword(mail_password);
	    
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
	    
	    return mailSender;
	}
}
