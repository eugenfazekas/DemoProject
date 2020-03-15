package com.demoproject.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import com.demoproject.service.UserServiceImpl;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class SecurityConf extends WebSecurityConfigurerAdapter {
	
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//	    return super.userDetailsService();
//	}
//	
//	@Autowired
//	private UserDetailsService userService;
	
	@Autowired
	private UserServiceImpl	userDetailsService;

	PasswordEncoder passwordEncoder =
		    PasswordEncoderFactories.createDelegatingPasswordEncoder();
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)throws Exception {
		
//		auth.userDetailsService(userService);
		
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	
	}
	@Override 
	protected void configure(HttpSecurity httpSec) throws Exception {
		
		httpSec
		.authorizeRequests()
			.antMatchers("/admin/**").hasRole("ADMIN")      
			.antMatchers("/index").permitAll()
			.antMatchers("/indexback").permitAll()
			.antMatchers("/").permitAll()
			.antMatchers("/main").permitAll()
			.antMatchers("/layouts/main").permitAll()
			.antMatchers("/registration").permitAll()
			.antMatchers("/reg").permitAll()
			.antMatchers("/blog").permitAll()
			.antMatchers("/blogs").permitAll()
			.antMatchers("/videos").permitAll()
			.antMatchers("/blogreg").permitAll()
			.antMatchers("/blogsreturn").permitAll()
			.antMatchers("/searchreturn").permitAll()
			.antMatchers("/description").permitAll()
			.antMatchers("/contact").permitAll()
			.antMatchers("/activation/**").permitAll()
			.antMatchers("/getadminkey").permitAll() 
			.antMatchers("/adminrequestcode/**").permitAll()
			.antMatchers("/adminreg").permitAll()
			.antMatchers("/adminregistration").permitAll()
			.antMatchers("/error/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin()
			.loginPage("/login")
			.permitAll()
			.and()
		.logout()
			.logoutSuccessUrl("/login?logout")
			.permitAll();
}	
	
		
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers("/css/**");
	    web.ignoring().antMatchers("/img/**");
	    web.ignoring().antMatchers("/templates/**");
	    
	    
	}
	 public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry.addResourceHandler(
	                "/webjars/**",
	                "/img/**",
	                "/css/**",
	                "/js/**")
	                .addResourceLocations(
	                        "classpath:/META-INF/resources/webjars/",
	                        "classpath:/static/img/",
	                        "classpath:/static/css/",
	                        "classpath:/static/js/");
}
}