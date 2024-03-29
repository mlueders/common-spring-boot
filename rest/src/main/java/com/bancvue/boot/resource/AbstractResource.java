/**
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
package com.bancvue.boot.resource;

import com.bancvue.boot.config.RequestScopedJerseyContext;
import java.lang.annotation.Annotation;
import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public abstract class AbstractResource {

	@Autowired
	private RequestScopedJerseyContext requestScopedJerseyContext;

	protected EnvelopeResponseFactory envelopeResponseFactory;

	/**
	 * The UriInfo is set by jersey per-request.  The spring request-scoped context is used to store the UriInfo
	 * in a thread(request)-safe manner.  Non-obvious, but functional.
	 */
	@Context
	public void setUriInfo(UriInfo uriInfo) {
		requestScopedJerseyContext.setUriInfo(uriInfo);
	}

	@PostConstruct
	private void postConstruct() {
		assertResourceAnnotatedWithComponent();
		envelopeResponseFactory = new EnvelopeResponseFactory(getClass(), requestScopedJerseyContext);
	}

	/**
	 * The resource needs to be annotated with @Component in order for codahale metrics annotations to function
	 * properly when using the metrics-spring bridge project.
	 */
	private void assertResourceAnnotatedWithComponent() {
		assertAnnotationExists(Component.class);
	}

	private <T extends Annotation> T assertAnnotationExists(Class<T> annotationType) {
		T annotation = getClass().getAnnotation(annotationType);
		if (annotation == null) {
			throw new IllegalStateException("REST resource must be annotated with @" + annotationType.getName());
		}
		return annotation;
	}

}
