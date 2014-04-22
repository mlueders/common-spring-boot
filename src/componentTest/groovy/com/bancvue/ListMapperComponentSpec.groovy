package com.bancvue
import com.bancvue.mapping.ListMapper
import org.dozer.DozerBeanMapper
import org.dozer.Mapper
import org.dozer.converters.ConversionException
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

	def "should throw conversion exception if cannot convert types while mapping"() {
		given:
		List<OrigType> originalValues = Arrays.asList(new OrigType([foo:"1", bar:"two", extra:"three"]))

		when:
		ListMapper.map(mapper, originalValues, NumericMappingType.class)

		then:
		thrown(ConversionException)
	}

}
