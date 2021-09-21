package com.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Value("${keystore.password}")
    private String password;
	
	@Value("${keystore.privateKey}")
    private String privateKey;
	
	@Value("${keystore.alias}")
    private String alias;
	
	@Value("${client.secret}")
    private String secret;
	
	@Value("${resourceService.client-id}")
    private String resourceServerClientId;
	
	@Value("${resourceService.client-secret}")
    private String resourceServerSecret;
  
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired 
	private BCryptPasswordEncoder passwordEncoder;

	@Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients.inMemory()
                .withClient("client")
                .secret(passwordEncoder.encode(secret))
                .authorizedGrantTypes("password")
                .scopes("read")
                .and()
                .withClient(resourceServerClientId)
                .secret(passwordEncoder.encode(resourceServerSecret));
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        
    	TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
    	List<TokenEnhancer> tokenEnhancers = List.of(jwtAccessTokenConverter());
        tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);

        endpoints
          .authenticationManager(authenticationManager)
          .tokenStore(tokenStore())
          .tokenEnhancer(tokenEnhancerChain);
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
    	log.debug("alias "+alias+" password "+password+" privatekey " + privateKey);
        var converter = new JwtAccessTokenConverter();
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                        new ClassPathResource(privateKey),
                        password.toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair(alias));
        return converter;
    }
    
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    	security.tokenKeyAccess("isAuthenticated()");
    }
}
