package com.bancvue.boot.cm.spring

import com.bancvue.cm.BVConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.PropertySources
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = ComponentTestContext)
class PropertySourceInitializerComponentSpec extends Specification {

	@Autowired
	ConfigurableApplicationContext context

	PropertySourceInitializer initializer = new PropertySourceInitializer()

	def setup() {
		System.setProperty(BVConfiguration.CONFIG, "src/componentTest/resources/componentTest.properties")
		initializer.initialize(context)
	}

	def "property source should be registered in environment"() {

		when:
		PropertySources propertySources = context.getEnvironment().getPropertySources()

		then:
		propertySources.contains(BVPropertiesPropertySource.DEFAULT_PROPERTY_SOURCE_NAME)
	}
}
