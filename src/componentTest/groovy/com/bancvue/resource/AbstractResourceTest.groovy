package com.bancvue.resource

import org.springframework.beans.factory.BeanCreationException
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import spock.lang.Specification
import spock.lang.Unroll

class AbstractResourceTest extends Specification {

	@Unroll
	def "should fail if resource not annotated with #expectedAnnotation"() {
		given:
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext()
		context.register(contextConfiguration)

		when:
		context.refresh()

		then:
		BeanCreationException ex = thrown()
		ex.cause instanceof IllegalStateException
		ex.cause.message =~ expectedExceptionMessage

		where:
		expectedAnnotation  | contextConfiguration                                     | expectedExceptionMessage
		"@Component"        | ResourceWithNoComponentAnnotationConfiguration           | /must be annotated with .*Component/
		"@Scope"            | ResourceWithNoScopeAnnotationConfiguration               | /must be annotated with .*Scope/
		'@Scope("request")' | ResourceWithScopeAnnotationOtherThanRequestConfiguration | /must be annotated with .*Scope/
	}

	@Configuration
	public static class ResourceWithNoComponentAnnotationConfiguration {
		@Bean
		ResourceWithNoComponentAnnotation getBean() {
			new ResourceWithNoComponentAnnotation();
		}

		@Scope("request")
		public static class ResourceWithNoComponentAnnotation extends AbstractResource {}
	}

	@Configuration
	public static class ResourceWithNoScopeAnnotationConfiguration {
		@Bean
		ResourceWithNoScopeAnnotation getBean() {
			new ResourceWithNoScopeAnnotation();
		}

		@Component
		public static class ResourceWithNoScopeAnnotation extends AbstractResource {}
	}

	@Configuration
	public static class ResourceWithScopeAnnotationOtherThanRequestConfiguration {
		@Bean
		ResourceWithScopeAnnotationOtherThanRequest getBean() {
			new ResourceWithScopeAnnotationOtherThanRequest();
		}

		@Component
		public static class ResourceWithScopeAnnotationOtherThanRequest extends AbstractResource {}

	}

}
