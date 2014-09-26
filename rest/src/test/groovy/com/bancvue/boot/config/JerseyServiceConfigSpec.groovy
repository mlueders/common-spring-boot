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
package com.bancvue.boot.config

import spock.lang.Specification

class JerseyServiceConfigSpec extends Specification {

	private JerseyServiceConfig config = new JerseyServiceConfig(Object.class)

	def "resolveApplicationPathFromAnnotation should fail if config class not annotated with ApplicationPath"() {
		when:
		config.resolveApplicationPathFromAnnotation()

		then:
		thrown(JerseyServiceConfig.ApplicationPathAnnotationMissingException)
	}

	def "resolveApplicationPath should fail if application path does not start with '/\\w'"() {
		when:
		config.resolveApplicationPath(path)

		then:
		thrown(JerseyServiceConfig.InvalidApplicationPathException)

		where:
		description | path
		"empty"     | ""
		"/"         | "/"
		"/*"        | "/*"
	}

	def "resolveApplicationPath should append /* to end of path if necessary"() {
		expect:
		config.resolveApplicationPath(path) == "/path/*"

		where:
		na | path
		"" | "/path"
		"" | "/path/"
		"" | "/path/*"
	}

}
