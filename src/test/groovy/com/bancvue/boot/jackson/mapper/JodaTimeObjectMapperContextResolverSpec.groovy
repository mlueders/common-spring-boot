package com.bancvue.boot.jackson.mapper

import groovy.transform.EqualsAndHashCode
import org.joda.time.LocalDate
import spock.lang.Specification

class JodaTimeObjectMapperContextResolverSpec extends Specification {

	def dateMapper = new JodaTimeObjectMapperContextResolver()

	@EqualsAndHashCode
	static class LocalDateHolder {
		LocalDate localDate
	}

	def "should serialize date correctly"() {
		given:
		String json = "{\"localDate\":\"2014-01-01\"}";
		LocalDateHolder localDateHolder = new LocalDateHolder(
				localDate: new LocalDate("2014-01-01")
		)

		expect:
		dateMapper.getContext(null).writeValueAsString(localDateHolder) == json
	}

	def "should deserialize date correctly"() {
		given:
		String json = "{\"localDate\":\"2014-01-01\"}";
		LocalDateHolder localDateHolder = new LocalDateHolder(
				localDate: new LocalDate("2014-01-01")
		)

		expect:
		dateMapper.getContext(null).readValue(json, LocalDateHolder.class) == localDateHolder
	}

}

