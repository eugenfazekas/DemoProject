package com.skynet.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;


@Service
public class MyCommandLineRunner implements CommandLineRunner{

	
	private final Logger log = LoggerFactory.getLogger(this.getClass()); 
	
	 @Value("${server.ssl.client-auth}")
	    private String clientauth;
	 
	 @Value("${spring.profiles.active}")
	    private String profile;
	 
	@Override
	public void run(String... args) throws Exception {

		log.debug("server.ssl.client-auth: "+ clientauth );
		log.debug("spring.profiles.active: "+ 	 profile );		
	} 
}
