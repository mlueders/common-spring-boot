/*
 * Copyright 2014 ${name}
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

