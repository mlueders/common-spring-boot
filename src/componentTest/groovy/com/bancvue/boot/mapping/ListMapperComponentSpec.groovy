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
package com.bancvue.boot.mapping

import com.bancvue.boot.mapping.ListMapper
import org.dozer.DozerBeanMapper
import org.dozer.Mapper
import org.unitils.reflectionassert.ReflectionAssert
import spock.lang.Specification

class ListMapperComponentSpec extends Specification {

	static class OrigType {
		String foo
		String bar
		String extra
	}

	static class NewType {
		String foo
		String bar
	}

	static class NumericMappingType {
		Integer foo
		Integer bar
	}


	Mapper mapper

	def setup() {
		mapper = new DozerBeanMapper()
	}

	def "should map original list to list of given type"() {
		given:
		List<OrigType> originalList = new ArrayList()
		originalList.add(new OrigType([foo:"1", bar:"2", extra:"3"]))
		originalList.add(new OrigType([foo:"a", bar:"b", extra:"c"]))

		def expectedTypes = [new NewType([foo:"1", bar:"2"]), new NewType([foo:"a", bar:"b"])]

		when:
		List<NewType> newList = ListMapper.map(mapper, originalList, NewType.class)

		then:
		newList.size() == 2
		ReflectionAssert.assertLenientEquals(expectedTypes, newList)
	}

}
