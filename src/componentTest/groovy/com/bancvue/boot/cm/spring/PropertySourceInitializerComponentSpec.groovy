/*
 * Copyright 2014 BancVue, LTD
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
