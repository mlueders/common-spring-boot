package com.bancvue.cm.spring;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertySource;

public class PropertySourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		PropertySource bvPropertySource = new BVPropertiesPropertySource();
		applicationContext.getEnvironment().getPropertySources().addFirst(bvPropertySource);
	}
}
