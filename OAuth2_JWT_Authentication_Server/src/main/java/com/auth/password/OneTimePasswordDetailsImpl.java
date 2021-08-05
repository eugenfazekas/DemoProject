package com.auth.password;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.model.OneTimePassword;

@Service
public class OneTimePasswordDetailsImpl  implements UserDetails{

	private static final long serialVersionUID = 1L;		
	private OneTimePassword oneTimePassword;
	private List<String> userAuthorities;
	
	public OneTimePasswordDetailsImpl() {
	}

	public OneTimePasswordDetailsImpl(OneTimePassword oneTimePassword, List<String> userAuthorities) {
		this.oneTimePassword = oneTimePassword;
		this.userAuthorities = userAuthorities;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		
		for (String authority : userAuthorities) {
		
			authorities.add(new SimpleGrantedAuthority(authority));
		}
		return authorities;
	}

	public String getPassword() {		
		return oneTimePassword.getPassword();
	}

	public String getUsername() {	
		return oneTimePassword.getEmail();
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}
	
	public String getId() {
		return oneTimePassword.getId();
	}
}
