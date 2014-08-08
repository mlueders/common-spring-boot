package com.bancvue.boot.client
import com.bancvue.boot.api.ApiEntity
import javax.ws.rs.client.WebTarget
import spock.lang.Specification

class CrudClientSupportSpec extends Specification {

	def "pathFor should throw exception if segment is null"(){
		given:
		WebTarget resource = Mock()
		CrudClientSupport crudClientSupport = new CrudClientSupport(resource, null, null)
		def segment = null

		when:
		crudClientSupport.pathFor(segment)

		then:
		thrown(IllegalArgumentException)
	}

	def "pathFor should throw exception if ApiEntity segment has a null id"(){
		given:
		WebTarget resource = Mock()
		CrudClientSupport crudClientSupport = new CrudClientSupport(resource, null, null)
		ApiEntity<Integer> segment = new ApiEntity<Integer>() {
			@Override
			Integer getId() {
				return null
			}
		}

		when:
		crudClientSupport.pathFor(segment)

		then:
		thrown(IllegalArgumentException)
	}
}
