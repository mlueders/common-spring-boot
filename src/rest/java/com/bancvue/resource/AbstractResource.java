package com.bancvue.resource;

import com.bancvue.rest.resource.ResourceResponseFactory;
import java.lang.annotation.Annotation;
import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;


@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AbstractResource {

	protected ResourceResponseFactory resourceResponseFactory;

	@Context
	public void setUriInfo(UriInfo uriInfo) {
		if (resourceResponseFactory != null) {
			// should never happen since we're ensuring @Scope("request") on post-construct but extra validation doesn't hurt
			throw new RuntimeException("Attempting to re-initialize ResourceResponseFactory");
		}
		resourceResponseFactory = new ResourceResponseFactory(this.getClass(), uriInfo);
	}

	@PostConstruct
	private void assertRequiredAnnotationsPresent() {
		assertResourceAnnotatedWithComponent();
		assertResourceAnnotatedWithRequestScope();
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

	/**
	 * The resource needs to be annotated with @Scope("request") b/c setUriInfo will be called by Jersey on each
	 * request, even if the bean is singleton scope.  This could lead to interesting and hard-to-detect defects
	 * since the UriInfo (and corresponding resourceResponseFactory) could be changing mid-request.
	 */
	private void assertResourceAnnotatedWithRequestScope() {
		Scope scope = getClass().getAnnotation(Scope.class);
		if ((scope == null) || !WebApplicationContext.SCOPE_REQUEST.equals(scope.value())) {
			throw new IllegalStateException("REST resource must be annotated with @Scope(WebApplicationContext.SCOPE_REQUEST)");
		}
	}

}
