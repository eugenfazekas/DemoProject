package com.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

	@Value("${redisserver.server}")
	private String redisServer;
	
	@Value("${redisserver.port}")
	private String redisPort;
	
	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
	String hostname = redisServer;
	int port = Integer.parseInt(redisPort);
	RedisStandaloneConfiguration redisStandaloneConfiguration
	= new RedisStandaloneConfiguration(hostname, port);
	return new JedisConnectionFactory(redisStandaloneConfiguration);
	}
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
	RedisTemplate<String, Object> template = new RedisTemplate<>();
	template.setConnectionFactory(jedisConnectionFactory());
	return template;
	}
}
