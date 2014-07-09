package com.bancvue.boot.resource;

import com.bancvue.boot.api.ApiEntity;
import com.bancvue.rest.Envelope;
import com.bancvue.rest.jaxrs.UriInfoHolder;
import com.bancvue.rest.params.AbstractParam;
import com.bancvue.rest.resource.ResourceResponseFactory;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.core.Response;

public class ApiEntityResponseFactory {

	private String basePath;
	private ResourceResponseFactory resourceResponseFactory;
	private HashMap<String, Object> templateValues;

	public ApiEntityResponseFactory(Class targetResource, UriInfoHolder uriInfoHolder) {
		this(new ResourceResponseFactory(targetResource, uriInfoHolder), "/", new HashMap<String, Object>());
	}

	private ApiEntityResponseFactory(ResourceResponseFactory resourceResponseFactory, String basePath,
	                                 HashMap<String, Object> templateValues) {
		this.basePath = basePath;
		this.resourceResponseFactory = resourceResponseFactory;
		this.templateValues = templateValues;
	}

	public ApiEntityResponseFactory path(String subpath) {
		if (subpath.startsWith("/")) {
			subpath = subpath.substring(1);
		}
		if (subpath.endsWith("/") == false) {
			subpath += "/";
		}

		return new ApiEntityResponseFactory(resourceResponseFactory, basePath + subpath, templateValues);
	}

	public ApiEntityResponseFactory template(String key, ApiEntity entity) {
		return template(key, entity.getId());
	}

	public ApiEntityResponseFactory template(String key, Object value) {
		HashMap<String, Object> newTemplateValues = new HashMap<>(templateValues);
		newTemplateValues.put(key, value);
		return new ApiEntityResponseFactory(resourceResponseFactory, basePath, newTemplateValues);
	}

	private URI getEntityResourceUri(ApiEntity entity) {
		return resourceResponseFactory.getTargetResourceLocation(pathFor(entity), templateValues);
	}

	public Response createGetManyResponse(List<? extends ApiEntity> entities) {
		return resourceResponseFactory.createGetManyResponse(envelope(entities));
	}

	@Deprecated
	public <T> Response createGetResponse(AbstractParam<T> entityId, ApiEntity<T> entity) {
		return createGetResponse(entity);
	}

	@Deprecated
	public <T> Response createGetResponse(T entityId, ApiEntity<T> entity) {
		return createGetResponse(entity);
	}

	/**
	 * In other methods, path is derived from the input entity.  In this case, the input entity could be null (resulting
	 * in a NOT_FOUND response from the ResourceResponseFactory) so entityId must be provided separately.
	 */
	public <T> Response createGetResponse(ApiEntity<T> entity) {
		return resourceResponseFactory.createGetResponse(envelope(entity));
	}

	public Response createPostSuccessResponse(ApiEntity insertedEntity) {
		URI location = getEntityResourceUri(insertedEntity);
		return resourceResponseFactory.createPostSuccessResponse(location, envelope(insertedEntity));
	}

	public Response createPostFailedBecauseAlreadyExistsResponse(ApiEntity entity) {
		return resourceResponseFactory.createPostFailedBecauseAlreadyExistsResponse(envelope(entity));
	}

	public Response createPutSuccessResponse(ApiEntity updatedEntity) {
		return resourceResponseFactory.createPutSuccessResponse(envelope(updatedEntity));
	}

	@Deprecated
	public <T> Response createDeleteResponse(AbstractParam<T> entityId, ApiEntity<T> entity) {
		return createDeleteResponse(entity);
	}

	@Deprecated
	public <T> Response createDeleteResponse(T entityId, ApiEntity<T> entity) {
		return createDeleteResponse(entity);
	}

	/**
	 * In other methods, path is derived from the input entity.  In this case, the input entity could be null (resulting
	 * in a NOT_FOUND response from the ResourceResponseFactory) so entityId must be provided separately.
	 */
	public <T> Response createDeleteResponse(ApiEntity<T> entity) {
		return resourceResponseFactory.createDeleteResponse(envelope(entity));
	}

	@Deprecated
	public Response createNotFoundResponse(Object entityId) {
		return createNotFoundResponse();
	}

	public Response createNotFoundResponse() {
		return resourceResponseFactory.createNotFoundResponse();
	}

	public Response createConflictResponse(ApiEntity entity) {
		return resourceResponseFactory.createConflictResponse(envelope(entity));
	}

	@Deprecated
	public Response createConflictResponse() {
		return resourceResponseFactory.createConflictResponse();
	}

	public Response createSeeOtherResponse(ApiEntity entity) {
		URI location = getEntityResourceUri(entity);
		return resourceResponseFactory.createSeeOtherResponse(location);
	}

	private String pathFor(Object id) {
		return basePath + id.toString();
	}

	private String pathFor(ApiEntity entity) {
		return pathFor(entity.getId());
	}

	private <T> Envelope<T> envelope(T entity) {
		return new Envelope.Builder<>(entity).build();
	}

}
