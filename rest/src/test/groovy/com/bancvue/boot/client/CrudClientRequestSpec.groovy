package com.bancvue.boot.client

import com.bancvue.boot.api.ApiEntity
import com.bancvue.rest.client.ClientRequest
import spock.lang.Specification

class CrudClientRequestSpec extends Specification {

	def "pathFor should throw exception if segment is null"(){
		given:
		ClientRequest request = Mock()
		CrudClientRequest crudClientSupport = new CrudClientRequest(request, null, null)
		def segment = null

		when:
		crudClientSupport.path(segment)

		then:
		thrown(IllegalArgumentException)
	}

	def "pathFor should throw exception if ApiEntity segment has a null id"(){
		given:
		ClientRequest request = Mock()
		CrudClientRequest crudClientSupport = new CrudClientRequest(request, null, null)
		ApiEntity<Integer> entity = new ApiEntity<Integer>() {
			@Override
			Integer getId() {
				return null
			}
		}

		when:
		crudClientSupport.path(entity)

		then:
		thrown(IllegalArgumentException)
	}
}
