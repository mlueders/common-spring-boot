package com.bancvue.boot.resource;

import com.bancvue.boot.api.ApiEntity;
import com.bancvue.rest.Envelope;
import com.bancvue.rest.jaxrs.UriInfoHolder;
import com.bancvue.rest.resource.ResourceResponseFactory;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.Response;

public class EnvelopeResponseFactory {

	private String basePath;
	private ResourceResponseFactory resourceResponseFactory;
	private HashMap<String, Object> templateValues;

	public EnvelopeResponseFactory(Class targetResource, UriInfoHolder uriInfoHolder) {
		this(new ResourceResponseFactory(targetResource, uriInfoHolder), "/", new HashMap<String, Object>());
	}

	private EnvelopeResponseFactory(ResourceResponseFactory resourceResponseFactory, String basePath,
	                                HashMap<String, Object> templateValues) {
		this.basePath = basePath.endsWith("/") ? basePath : basePath + "/";
		this.resourceResponseFactory = resourceResponseFactory;
		this.templateValues = templateValues;
	}

	public ResourceResponseFactory getResourceResponseFactory() {
		return resourceResponseFactory;
	}

	public EnvelopeResponseFactory path(Object segment) {
		return new EnvelopeResponseFactory(resourceResponseFactory, pathFor(segment), templateValues);
	}

	public EnvelopeResponseFactory template(String key, ApiEntity entity) {
		return template(key, entity.getId());
	}

	public EnvelopeResponseFactory template(String key, Object value) {
		HashMap<String, Object> newTemplateValues = new HashMap<>(templateValues);
		newTemplateValues.put(key, value);
		return new EnvelopeResponseFactory(resourceResponseFactory, basePath, newTemplateValues);
	}

	public URI getBaseResourceUri() {
		return resourceResponseFactory.getTargetResourceLocation(basePath, templateValues);
	}

	private URI getEntityResourceUri(ApiEntity entity) {
		return resourceResponseFactory.getTargetResourceLocation(pathFor(entity), templateValues);
	}

	public Response createGetManyResponse(Collection entities) {
		return resourceResponseFactory.createGetManyResponse(envelope(entities));
	}

	public Response createGetManyResponse(Map entities) {
		return resourceResponseFactory.createGetManyResponse(envelope(entities));
	}

	public Response createGetResponse(Object entity) {
		return resourceResponseFactory.createGetResponse(envelope(entity));
	}

	public Response createPostSuccessResponse(ApiEntity insertedEntity) {
		URI location = getEntityResourceUri(insertedEntity);
		return createPostSuccessResponse(location, insertedEntity);
	}

	/**
	 * In the case of non-standard posts, the URI can be built up using the <code>path</code> and
	 * <code>template</code> methods and created with <code>getBaseResourceUri</code>.  Something like...
	 * <code>
	 * URI location = entityResponseFactory.path("somepath").getBaseResourceUri();
	 * entityResponseFactory.createPostSuccessResponse(location, someObject);
	 * </code>
	 *
	 */
	public Response createPostSuccessResponse(URI location, Object insertedEntity) {
		return resourceResponseFactory.createPostSuccessResponse(location, envelope(insertedEntity));
	}

	public Response createPostFailedBecauseAlreadyExistsResponse(Object entity) {
		return resourceResponseFactory.createPostFailedBecauseAlreadyExistsResponse(envelope(entity));
	}

	public Response createPutSuccessResponse(Object updatedEntity) {
		return resourceResponseFactory.createPutSuccessResponse(envelope(updatedEntity));
	}

	public Response createDeleteResponse(Object entity) {
		return resourceResponseFactory.createDeleteResponse(envelope(entity));
	}

	public Response createNotFoundResponse() {
		return resourceResponseFactory.createNotFoundResponse();
	}
	
	public Response createForbiddenResponse() {
		return resourceResponseFactory.createForbiddenResponse();
	}

	public Response createConflictResponse(Object entity) {
		return resourceResponseFactory.createConflictResponse(envelope(entity));
	}

	public Response createSeeOtherResponse(ApiEntity entity) {
		URI location = getEntityResourceUri(entity);
		return createSeeOtherResponse(location);
	}

	public Response createSeeOtherResponse(URI location) {
		return resourceResponseFactory.createSeeOtherResponse(location);
	}

	private String pathFor(Object segment) {
		if (segment instanceof ApiEntity) {
			segment = ((ApiEntity) segment).getId();
		}

		String segmentString = segment.toString();
		if (segmentString.startsWith("/")) {
			segmentString = segmentString.substring(1);
		}
		return basePath + segmentString;
	}

	private <T> Envelope<T> envelope(T entity) {
		return new Envelope.Builder<>(entity).build();
	}

}
