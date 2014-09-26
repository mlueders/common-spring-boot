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

import com.bancvue.boot.api.ApiEntity
import com.bancvue.rest.client.request.ClientRequest
import spock.lang.Specification

class CrudClientRequestSpec extends Specification {

	def "path should throw exception if segment is null"(){
		given:
		ClientRequest request = Mock()
		CrudClientRequest crudClientSupport = new CrudClientRequest(request, null, null)
		def segment = null

		when:
		crudClientSupport.path(segment)

		then:
		thrown(IllegalArgumentException)
	}

	def "path should throw exception if ApiEntity segment has a null id"(){
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
