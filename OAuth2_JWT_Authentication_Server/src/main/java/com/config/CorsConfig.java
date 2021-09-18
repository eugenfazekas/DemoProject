package com.config;


//@Configuration
public class CorsConfig {
	/* 
	 @Bean
	    public FilterRegistrationBean<CorsFilter> customCorsFilter() {
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        CorsConfiguration config = new CorsConfiguration();
	        config.setAllowCredentials(true);
	        config.setAllowedMethods(Collections.singletonList("*"));
	        config.setAllowedHeaders(Collections.singletonList("*"));
	        config.addAllowedOrigin("http://localhost:4200");
	        config.setAllowCredentials(true);
	        source.registerCorsConfiguration("/**", config);
	        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));

	        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
	        return bean;
	    }
	    */
}
