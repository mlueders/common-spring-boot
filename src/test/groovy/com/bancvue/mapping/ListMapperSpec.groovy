package com.bancvue.mapping

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

