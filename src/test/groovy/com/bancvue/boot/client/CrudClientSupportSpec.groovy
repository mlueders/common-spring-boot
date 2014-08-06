package com.bancvue.boot.client

import com.bancvue.boot.api.ApiValueObject
import javax.ws.rs.client.WebTarget
import spock.lang.Specification

class CrudClientSupportSpec extends Specification {

	def "should return resource if segment is a value object"() {
		given:
		WebTarget resource = Mock()
		CrudClientSupport crudClientSupport = new CrudClientSupport(resource, null, null)
		ApiValueObject segment = new ApiValueObject() {}

		expect:
		crudClientSupport.pathFor(segment) == resource
	}
}
