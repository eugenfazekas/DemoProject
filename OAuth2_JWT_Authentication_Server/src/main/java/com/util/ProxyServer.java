package com.util;

import java.io.Serializable;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.web.client.RestTemplate;

import com.model.ProxyUser;

@Component
public class ProxyServer {

	private final Logger log = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired
	private RestTemplate rest;
	
	@Autowired
	private AuthorizationServerEndpointsConfiguration configuration;
	
	@Value("${resource.service.base.url}")
	private String baseURL;
	
	public void sendNewUserId(String id) {
		String url = baseURL + "/api2/v1//user/createUserResource";
		
		var body = new ProxyUser();
		body.setId(id);
		
		var request = new HttpEntity<>(body,createHeader(generateOAuth2AccessToken()));
		
		try {		
			var response = rest.postForEntity(url, request, Void.class);	
			log.debug("Resource Server Status = "+response.getStatusCode().toString()+ " with Id " +id);
		}catch (Exception e) {
			log.debug("Resource Server Not Found");
		}
	}

	private String generateOAuth2AccessToken () {

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
	
		private HttpHeaders  createHeader(String accessToken) {	
				HttpHeaders header = new HttpHeaders();
				header.setContentType(MediaType.APPLICATION_JSON);
				header.set("Authorization", "Bearer "+ accessToken);			
					return header;
		}

}