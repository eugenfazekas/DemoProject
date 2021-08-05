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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import com.auth.password.OneTimePasswordAuthenticationProviderService;
import com.auth.user.UserAuthenticationProviderService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Autowired
    private UserAuthenticationProviderService userAuthenticationProvider;
	
	@Autowired
    private OneTimePasswordAuthenticationProviderService oneTimePasswordAuthenticationProvider;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    } 
    
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
    	http.authorizeRequests().mvcMatchers("user/**").permitAll()
    	    .and().authorizeRequests().anyRequest().authenticated();
    }
}
