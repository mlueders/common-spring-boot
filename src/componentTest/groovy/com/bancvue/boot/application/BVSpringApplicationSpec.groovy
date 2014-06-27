package com.bancvue.boot.application

import com.bancvue.boot.testsupport.ComponentTestConfig
import com.bancvue.cm.BVConfiguration
import org.springframework.context.ConfigurableApplicationContext
import spock.lang.Specification;


public class BVSpringApplicationSpec extends Specification {

	def setup(){
		System.setProperty(BVConfiguration.CONFIG, "src/componentTest/resources/componentTest.properties")
	}

	def cleanup(){
		System.getProperties().remove(BVConfiguration.CONFIG)	
	}
	
	def "should load Application Context"(){
		given:
		BVSpringApplication bvSpringApplication = new BVSpringApplication(SimpleTestComponentConfig.class);
		
		when:
		ConfigurableApplicationContext applicationContext = bvSpringApplication.run("hello")
		
		then:
		applicationContext.getBean("hello")
		
		cleanup:
		applicationContext.close()

	}
}
