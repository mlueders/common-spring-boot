package com.bancvue.boot.client

import com.bancvue.rest.envelope.DefaultEnvelope
import java.lang.reflect.ParameterizedType
import javax.ws.rs.core.GenericType
import spock.lang.Specification

class GenericTypeFactoryTest extends Specification {

	private GenericTypeFactory factory = new GenericTypeFactory()

	def "should create simple GenericType"() {
		when:
		GenericType genericType = factory.createGenericType(String.class)

		then:
		genericType.rawType == String.class
		genericType.type == String.class
	}

	def "should create parameterized GenericType"() {
		when:
		GenericType genericType = factory.createGenericType(List.class, String.class)

		then:
		genericType.rawType == List.class
		ParameterizedType parameterizedType = genericType.type
		parameterizedType.rawType == List.class
		parameterizedType.actualTypeArguments == [String.class]
	}

	def "should create complex parameterized GenericType"() {
		when:
		GenericType genericType = factory.createGenericType(DefaultEnvelope.class, List.class, String.class)

		then:
		genericType.getRawType() == DefaultEnvelope.class
		ParameterizedType outerParameterizedType = genericType.getType()
		outerParameterizedType.rawType == DefaultEnvelope.class
		ParameterizedType innerParameterizedType = outerParameterizedType.actualTypeArguments[0]
		innerParameterizedType.rawType == List.class
		innerParameterizedType.actualTypeArguments == [String.class]
	}

}
