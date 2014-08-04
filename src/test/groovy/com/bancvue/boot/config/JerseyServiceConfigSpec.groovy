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
