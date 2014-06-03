package com.bancvue.boot.cm.spring

import com.bancvue.cm.ConfigurationRuleUtil
import org.springframework.boot.SpringApplication
import org.springframework.boot.test.SpringApplicationContextLoader

public class BVConfigContextLoader extends SpringApplicationContextLoader {

	@Override
	protected SpringApplication getSpringApplication() {
		ConfigurationRuleUtil.useBVConfiguration()
		SpringApplication app = new SpringApplication();
		app.addInitializers(new PropertySourceInitializer());
		return app;
	}
}