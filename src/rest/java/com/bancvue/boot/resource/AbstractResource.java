package com.bancvue.boot.resource;

import com.bancvue.boot.api.ApiEntity;
import com.bancvue.boot.config.RequestScopedJerseyContext;
import com.bancvue.rest.Envelope;
import com.bancvue.rest.resource.ResourceResponseFactory;
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

	protected ResourceResponseFactory resourceResponseFactory;

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
		resourceResponseFactory = new ResourceResponseFactory(this.getClass(), requestScopedJerseyContext);
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

	protected static String pathFor(Long id) {
		return "/" + id;
	}

	protected static String pathFor(ApiEntity entity) {
		return pathFor(entity.getId());
	}

	protected static <T> Envelope<T> envelope(T entity) {
		return new Envelope.Builder<>(entity).build();
	}
}
