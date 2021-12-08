package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.auth.password.OneTimePasswordAuthenticationProviderService;
import com.auth.user.UserAuthenticationProviderService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Autowired
    private UserAuthenticationProviderService userAuthenticationProvider;
	
	@Autowired
    private OneTimePasswordAuthenticationProviderService oneTimePasswordAuthenticationProvider;
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(userAuthenticationProvider);
        auth.authenticationProvider(oneTimePasswordAuthenticationProvider);
    }
    
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
    
    @Override
    protected void configure (HttpSecurity http) throws Exception {
    	http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    	http.httpBasic();
    	http.authorizeRequests().mvcMatchers("api1/v1/user/**").permitAll()
    							.mvcMatchers("actuator/**").permitAll()
    	    .and().authorizeRequests().anyRequest().authenticated();
    }
}
