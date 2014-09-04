package com.bancvue.boot.resource

import com.bancvue.boot.config.RequestScopedJerseyContext
import org.springframework.beans.factory.BeanCreationException
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import spock.lang.Specification
import spock.lang.Unroll

class AbstractResourceTest extends Specification {

	@Unroll
	def "should fail if resource not annotated with @Component"() {
		given:
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext()
		context.register(ResourceWithNoComponentAnnotationConfiguration)

		when:
		context.refresh()

		then:
		BeanCreationException ex = thrown()
		ex.cause instanceof IllegalStateException
		ex.cause.message =~ /must be annotated with .*Component/
	}

	@Configuration
	public static class ResourceWithNoComponentAnnotationConfiguration {
		@Bean
		ResourceWithNoComponentAnnotation getBean() {
			new ResourceWithNoComponentAnnotation();
		}

		@Bean
		RequestScopedJerseyContext getJerseyContext() {
			return new RequestScopedJerseyContext()
		}

		public static class ResourceWithNoComponentAnnotation extends AbstractResource {}
	}

}
