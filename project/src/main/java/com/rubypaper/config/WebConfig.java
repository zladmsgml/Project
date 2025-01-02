package com.rubypaper.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	 public void addCorsMappings(CorsRegistry registry) {
		 registry.addMapping("/**")
	 	.allowCredentials(true)
	 	.allowedHeaders(HttpHeaders.AUTHORIZATION)
	 	.allowedMethods(HttpMethod.GET.name(),
	 					HttpMethod.POST.name(),
	 					HttpMethod.PUT.name(),
	 					HttpMethod.DELETE.name())

	 	.allowedOrigins("http://localhost:3000", "http://10.125.121.217:3000")
	 	.exposedHeaders(CorsConfiguration.ALL);
	 }
}