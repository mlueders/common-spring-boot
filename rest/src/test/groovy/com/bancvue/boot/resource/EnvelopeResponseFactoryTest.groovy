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
package com.bancvue.boot.resource

import com.bancvue.boot.api.ApiEntity
import com.bancvue.rest.jaxrs.UriInfoHolder
import javax.ws.rs.Path
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriBuilder
import javax.ws.rs.core.UriInfo
import spock.lang.Specification

class EnvelopeResponseFactoryTest extends Specification {

	UriInfoHolder uriInfoHolder = Mock()
	EnvelopeResponseFactory responseFactory = new EnvelopeResponseFactory(Widget.class, uriInfoHolder);

	def "createConflictResponse with an entity should return a conflict status with an entity"() {
		given:
		Widget widget = new Widget(5)

		when:
		Response response = responseFactory.createConflictResponse(widget)

		then:
		response.status == 409
		response.entity != null
		response.entity.data == widget
	}

	def "should return new EnvelopResponseFactory with basePath updated with input argument"() {
		when:
		EnvelopeResponseFactory updatedResponseFactory = responseFactory.path("subpath")

		then:
		!updatedResponseFactory.is(responseFactory)
		updatedResponseFactory.basePath == "/subpath/"
		assert responseFactory.basePath == "/"
	}

	def "should use id as input path if input argument is ApiEntity"() {
		given:
		Widget widget = new Widget(10)

		when:
		EnvelopeResponseFactory updatedResponseFactory = responseFactory.path(widget)

		then:
		updatedResponseFactory.basePath == "/10/"
	}

	def "should use template values when building post success response"() {
		given:
		Widget widget = new Widget(15)
		UriInfo uriInfo = Mock()
		uriInfoHolder.getUriInfo() >> { uriInfo }
		uriInfo.getBaseUriBuilder() >> { UriBuilder.fromPath("/") }

		when:
		Response response = responseFactory.path("{key}").template("key", "value").createPostSuccessResponse(widget)

		then:
		response.getLocation().toString() == "/value/15"
	}

	@Path("/")
	static class Widget implements ApiEntity<Long>{

		Long id

		Widget(Long id) {
			this.id = id
		}

		@Override
		Long getId() {
			id
		}
	}
}
