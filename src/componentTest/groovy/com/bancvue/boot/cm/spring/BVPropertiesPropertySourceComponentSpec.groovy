package com.bancvue.boot.cm.spring

import spock.lang.Specification

class BVPropertiesPropertySourceComponentSpec extends Specification {
	BVPropertiesPropertySource propertySource

	def setup() {
		System.setProperty("bv.config", "src/componentTest/resources/componentTest.properties")
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
