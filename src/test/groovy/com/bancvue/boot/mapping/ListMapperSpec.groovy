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

import org.dozer.DozerBeanMapper
import org.dozer.Mapper
import spock.lang.Specification


class ListMapperSpec extends Specification {

	static class OrigType {
		String foo
		String bar
		String extra
	}

	static class NewType {
		String foo
		String bar
	}

	Mapper mapper

	def setup() {
		mapper = Mock(DozerBeanMapper)
	}

	def "should call mapper for each element in original list"() {
		given:
		List<OrigType> originalList = new ArrayList()
		originalList.add(new OrigType([foo:"1", bar:"2", extra:"3"]))
		originalList.add(new OrigType([foo:"a", bar:"b", extra:"c"]))

		def newTypes = [new NewType([foo:"1", bar:"2"]), new NewType([foo:"a", bar:"b"])]
		2 * mapper.map(_, _) >>> newTypes

		when:
		List<NewType> newList = ListMapper.map(mapper, originalList, NewType.class)

		then:
		newList.size() == 2
		newList.containsAll(newTypes)
	}
}

