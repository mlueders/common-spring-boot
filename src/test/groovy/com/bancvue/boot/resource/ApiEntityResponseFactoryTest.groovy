package com.bancvue.boot.resource

import com.bancvue.boot.api.ApiEntity
import com.bancvue.rest.jaxrs.UriInfoHolder
import javax.ws.rs.core.Response
import spock.lang.Specification

class ApiEntityResponseFactoryTest extends Specification {
	ApiEntityResponseFactory responseFactory

	def setup() {
		UriInfoHolder uriInfoHolder = Mock()
		responseFactory = new ApiEntityResponseFactory(Widget.class, uriInfoHolder);
	}

	def "createConflictResponse return a conflict status with no entity"() {
		when:
		Response response = responseFactory.createConflictResponse()

		then:
		response.status == 409
		response.entity == null
	}

	def "createConflictResponse with an entityshould a conflict status with an entity"() {
		given:
		Widget widget = new Widget()

		when:
		Response response = responseFactory.createConflictResponse(widget)

		then:
		response.status == 409
		response.entity != null
		response.entity.data == widget
	}

	static class Widget implements ApiEntity<Long>{
		@Override
		Long getId() {
			return 42L
		}
	}
}
