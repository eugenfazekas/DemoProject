package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableEurekaClient
public class SpringBootMyProject01OAuth2JwtResourceServerApplication {
	 
	public static void main(String[] args) {
		SpringApplication.run(SpringBootMyProject01OAuth2JwtResourceServerApplication.class, args);
	}
}
