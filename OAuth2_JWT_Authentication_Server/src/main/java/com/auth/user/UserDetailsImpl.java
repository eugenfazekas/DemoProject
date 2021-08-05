package com.auth.user;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.model.User;

@Service
public class UserDetailsImpl  implements UserDetails{

	private static final long serialVersionUID = 1L;	
	
	private User user;

	public UserDetailsImpl() {

	}

	public UserDetailsImpl(User user) {
		this.user = user;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		
		for (String authority : user.getAuthorities()) {
		
			authorities.add(new SimpleGrantedAuthority(authority));
		}
		return authorities;
	}

	public String getPassword() {		
		return user.getPassword();
	}

	public String getUsername() {	
		return user.getEmail();
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
		return user.isActive();
	}

	public String getId() {
		return user.getId();
	}
	
	public boolean isMfa() {
		return user.isMfa();
	}
}
