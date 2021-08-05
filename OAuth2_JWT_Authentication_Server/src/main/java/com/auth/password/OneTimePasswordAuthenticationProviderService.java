package com.auth.password;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.service.OneTimePasswordService;

@Service
public class OneTimePasswordAuthenticationProviderService implements AuthenticationProvider {

	private BCryptPasswordEncoder passwordEncoder;

	public OneTimePasswordAuthenticationProviderService() {
		this.passwordEncoder =  new BCryptPasswordEncoder();
	}

	@Autowired
	private OneTimePasswordDetailsServiceImpl oneTimePasswordDetailsServiceImpl;
	
	@Autowired
	private OneTimePasswordService oneTimePasswordService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String username = authentication.getName();
		String code = authentication.getCredentials().toString();	
		OneTimePasswordDetailsImpl otpDetails = oneTimePasswordDetailsServiceImpl.loadUserByUsername(username);
		
		if(passwordEncoder.matches(code, otpDetails.getPassword())) {
			oneTimePasswordService.removeOneTimePassword(username);
			return new UsernamePasswordAuthenticationToken(
						otpDetails.getId(),
						code,
						otpDetails.getAuthorities()
					);
		} else {
			throw new BadCredentialsException("Something went wrong in authentication!");
		}
	}

	@Override
	public boolean supports(Class<?> authenticationType ) {

		return authenticationType.equals(UsernamePasswordAuthenticationToken.class);
	}

}
