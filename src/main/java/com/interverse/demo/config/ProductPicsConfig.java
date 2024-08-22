package com.interverse.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;


public class ProductPicsConfig {
	
	@Value("${resource.url}")
	private String resource;
	
	public void addResourceHandlers(ResourceHandlerRegistry registry ) {
		registry.addResourceHandler("/product_photos/**").addResourceLocations(resource);
	}
}
