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

import com.bancvue.cm.BVProperties
import spock.lang.Specification

class BVPropertiesPropertySourceSpec extends Specification {

	BVPropertiesPropertySource propertySource

	def setup() {
		System.setProperty("bv.config", "src/test/resources/test.properties")
		propertySource = new BVPropertiesPropertySource()
		propertySource.bvProperties = Mock(BVProperties)
	}

	def "getProperty should delegate to bvProperties"() {
		given:
		propertySource.bvProperties.getString("foo") >> "bar"

		when:
		def val = propertySource.getProperty("foo")

		then:
		val == "bar"
	}

	def "property source name should default to bvprops"() {
		when:
		def name = propertySource.getName()

		then:
		name == "bvprops"
	}

	def "property source name should be set when passed to constructor"() {
		given:
		propertySource = new BVPropertiesPropertySource("ps")

		when:
		def name = propertySource.getName()

		then:
		name == "ps"
	}
}
