package com.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
   
    @Override
	public void configure (HttpSecurity http) throws Exception {
    	http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    	http.authorizeRequests().mvcMatchers("/images/**").permitAll()
    			.requestMatchers(checkPort(managementPort)).permitAll()
    							.antMatchers("/admin/**").hasAuthority("admin")
    		.and().authorizeRequests().anyRequest().authenticated();
		http.cors().and();
    }

    @Value("${management.server.port}")
    private int managementPort;

    private RequestMatcher checkPort(final int port) {
        return (HttpServletRequest request) -> port == request.getLocalPort();
    }
}
