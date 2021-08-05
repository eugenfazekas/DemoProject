package com.auth.password;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.model.OneTimePassword;
import com.model.User;
import com.repository.OneTimePasswordRepository;
import com.repository.UserRepository;

@Service
public class OneTimePasswordDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OneTimePasswordRepository oneTimePasswordRepository;

	public OneTimePasswordDetailsImpl loadUserByUsername(String username)  {

		User user = userRepository.findByEmail(username);
		OneTimePassword otp = oneTimePasswordRepository.findOneTimePassword(username);
		if(user == null || otp == null || !user.isMfa()) {
			throw new UsernameNotFoundException(username);
		}
		return new OneTimePasswordDetailsImpl(otp, user.getAuthorities());
	}
}
