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
