package com.bancvue.mapping

import org.dozer.DozerBeanMapper
import org.dozer.Mapper
import spock.lang.Specification


class ListMapperSpec extends Specification {

	public class OrigType {
		String foo
		String bar
		String extra
	}

	public class NewType {
		String foo
		String bar
	}

	Mapper mapper

	def setup() {
		mapper = Mock(DozerBeanMapper)
	}

	def "should map original list to list of given type"() {
		given:
		List<OrigType> originalList = new ArrayList()
		originalList.add(new OrigType([foo:"1", bar:"2", extra:"3"]))
		originalList.add(new OrigType([foo:"a", bar:"b", extra:"c"]))

		def newTypes = [new NewType([foo:"1", bar:"2"]), new NewType([foo:"a", bar:"b"])]
		mapper.map(_, _) >>> newTypes

		when:
		List<NewType> newList = ListMapper.map(mapper, originalList, NewType.class)

		then:
		newList.size() == 2
		newList.containsAll(newTypes)
	}
}

