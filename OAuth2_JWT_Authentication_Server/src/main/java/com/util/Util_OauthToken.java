package com.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Component;

@Component
public class Util_OauthToken {
	
	@Autowired
	private  AuthorizationServerEndpointsConfiguration configuration;
	
	public String generateOAuth2AccessToken () {

	    Map<String, String> requestParameters = new HashMap<String, String>();
	    Map<String, Serializable> extensionProperties = new HashMap<String, Serializable>();

	    List<String> scopes = new ArrayList<>();
	    scopes.add("read");
	    
	    boolean approved = true;
	    Set<String> responseTypes = new HashSet<String>();
	    responseTypes.add("code");

	    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	    
	        authorities.add(new SimpleGrantedAuthority("user"));

	    OAuth2Request oauth2Request = new OAuth2Request(requestParameters, "client", authorities, approved, new HashSet<String>(scopes), null, null, responseTypes, extensionProperties);

	    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("AuthServer", "AuthServerPass", authorities);

	    OAuth2Authentication auth = new OAuth2Authentication(oauth2Request, authenticationToken);

	    AuthorizationServerTokenServices tokenService = configuration.getEndpointsConfigurer().getTokenServices();

	    OAuth2AccessToken token = tokenService.createAccessToken(auth);
	    
	    return token.getValue();
	    
		}
}
