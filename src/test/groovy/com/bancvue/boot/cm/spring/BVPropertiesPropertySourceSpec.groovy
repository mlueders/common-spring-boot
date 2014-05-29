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
