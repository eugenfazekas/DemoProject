package com.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.util.matcher.RequestMatcher;

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
        	.requestMatchers(checkPort(managementPort)).permitAll()
      .and().authorizeRequests().anyRequest().authenticated();
    }

    @Value("${management.server.port}")
    private int managementPort;

    private RequestMatcher checkPort(final int port) {
        return (HttpServletRequest request) -> port == request.getLocalPort();
    }

}
