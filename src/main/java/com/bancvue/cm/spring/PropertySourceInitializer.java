package com.bancvue.cm.spring;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.core.env.PropertySource;
import org.springframework.web.context.ConfigurableWebApplicationContext;

public class PropertySourceInitializer implements ApplicationContextInitializer<ConfigurableWebApplicationContext> {

	@Override
	public void initialize(ConfigurableWebApplicationContext applicationContext) {
		PropertySource bvPropertySource = new BVPropertiesPropertySource();
		applicationContext.getEnvironment().getPropertySources().addLast(bvPropertySource);
	}
}
