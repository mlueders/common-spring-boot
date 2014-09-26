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
import spock.lang.Specification

class BVPropertiesPropertySourceComponentSpec extends Specification {
	BVPropertiesPropertySource propertySource

	def setup() {
		System.setProperty(BVConfiguration.CONFIG, "src/componentTest/resources/componentTest.properties")
		propertySource = new BVPropertiesPropertySource()
	}

	def "getProperty should return property value as defined"() {
		when:
		def val = propertySource.getProperty("test.prop")

		then:
		val == "found"
	}

	def "getProperty should return null for undefined properties"() {
		when:
		def val = propertySource.getProperty("not.found")

		then:
		val == null
	}

	def "containsProperty should return true if defined"() {
		when:
		def val = propertySource.containsProperty("test.prop")

		then:
		val
	}

	def "containsProperty should return false for undefined properties"() {
		when:
		def val = propertySource.containsProperty("not.found")

		then:
		!val
	}

}
