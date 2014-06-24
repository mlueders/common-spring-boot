package com.bancvue.boot.testsupport

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ComponentTestConfig {

	@Bean
	public ResourceIntegrationTestSupport getResourceIntegrationTestSupport() {
		return new ResourceIntegrationTestSupport()
	}

}
