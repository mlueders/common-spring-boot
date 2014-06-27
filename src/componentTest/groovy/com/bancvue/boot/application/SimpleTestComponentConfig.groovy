package com.bancvue.boot.application

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableAutoConfiguration(exclude = HibernateJpaAutoConfiguration.class)
class SimpleTestComponentConfig {
	
	@Bean
	String hello(){
		return "hello";
	}
}
