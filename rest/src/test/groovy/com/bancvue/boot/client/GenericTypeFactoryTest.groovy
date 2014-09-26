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

	def "parameterized types created with the same arguments should be equal"() {
		when:
		GenericType genericType1 = factory.createGenericType(List.class, String.class)
		GenericType genericType2 = factory.createGenericType(List.class, String.class)

		then:
		genericType1 == genericType2
	}

}
