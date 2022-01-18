package com.skynet.gateway.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class SSL_Config implements EnvironmentAware {

	@Override
	public void setEnvironment(final Environment environment) {

		String keystoreLocation = environment.getProperty("server.ssl.key-store");
		String keystorePassword = environment.getProperty("server.ssl.key-store-password");
		String truststoreLocation = environment.getProperty("server.ssl.trust-store");
		String truststorePassword = environment.getProperty("server.ssl.trust-store-password");
		

		if (truststoreLocation != null && truststorePassword != null) {
			System.setProperty("javax.net.ssl.trustStore", truststoreLocation);
			System.setProperty("javax.net.ssl.trustStorePassword", truststorePassword);
		}

		if (keystoreLocation != null && keystorePassword != null) {
			System.setProperty("spring.cloud.gateway.httpclient.ssl.key-store", keystoreLocation);
			System.setProperty("spring.cloud.gateway.httpclient.ssl.key-store-password", keystorePassword);
		}

	}
	
}
