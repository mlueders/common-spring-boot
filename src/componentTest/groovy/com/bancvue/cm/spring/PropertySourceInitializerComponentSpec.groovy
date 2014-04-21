package com.bancvue.cm.spring
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.MutablePropertySources
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = ComponentTestContext)
class PropertySourceInitializerComponentSpec extends Specification {

	@Autowired
	ConfigurableApplicationContext context

	PropertySourceInitializer initializer = new PropertySourceInitializer()

	def setup() {
		System.setProperty("bv.config", "src/componentTest/resources/componentTest.properties")
		initializer.initialize(context)
	}

	def "property source should be registered in environment"() {

		when:
		MutablePropertySources propertySources = context.getEnvironment().getPropertySources()

		then:
		propertySources.contains("bvprops")
	}
}
