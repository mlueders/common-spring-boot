package com.bancvue.boot.resource;

import com.bancvue.boot.api.ApiEntity;
import com.bancvue.rest.Envelope;
import com.bancvue.rest.jaxrs.UriInfoHolder;
import com.bancvue.rest.params.AbstractParam;
import com.bancvue.rest.resource.ResourceResponseFactory;
import java.util.List;
import javax.ws.rs.core.Response;

public class ApiEntityResponseFactory {

	private String basePath;
	private ResourceResponseFactory resourceResponseFactory;

	public ApiEntityResponseFactory(Class targetResource, UriInfoHolder uriInfoHolder) {
		this(new ResourceResponseFactory(targetResource, uriInfoHolder), "/");
	}

	private ApiEntityResponseFactory(ResourceResponseFactory resourceResponseFactory, String basePath) {
		this.basePath = basePath;
		this.resourceResponseFactory = resourceResponseFactory;
	}

	public ApiEntityResponseFactory path(String subpath) {
		if (subpath.startsWith("/")) {
			subpath = subpath.substring(1);
		}
		if (subpath.endsWith("/") == false) {
			subpath += "/";
		}

		return new ApiEntityResponseFactory(resourceResponseFactory, basePath + subpath);
	}

	public Response createGetManyResponse(List<? extends ApiEntity> entities) {
		return resourceResponseFactory.createGetManyResponse(envelope(entities));
	}

	public <T> Response createGetResponse(AbstractParam<T> entityId, ApiEntity<T> entity) {
		return createGetResponse(entityId.get(), entity);
	}

	/**
	 * In other methods, path is derived from the input entity.  In this case, the input entity could be null (resulting
	 * in a NOT_FOUND response from the ResourceResponseFactory) so entityId must be provided separately.
	 */
	public <T> Response createGetResponse(T entityId, ApiEntity<T> entity) {
		return resourceResponseFactory.createGetResponse(pathFor(entityId), envelope(entity));
	}

	public Response createPostSuccessResponse(ApiEntity insertedEntity) {
		return resourceResponseFactory.createPostSuccessResponse(pathFor(insertedEntity), envelope(insertedEntity));
	}

	public Response createPostFailedBecauseAlreadyExistsResponse(ApiEntity entity) {
		return resourceResponseFactory.createPostFailedBecauseAlreadyExistsResponse(pathFor(entity), envelope(entity));
	}

	public Response createPutSuccessResponse(ApiEntity updatedEntity) {
		return resourceResponseFactory.createPutSuccessResponse(pathFor(updatedEntity), envelope(updatedEntity));
	}

	public <T> Response createDeleteResponse(AbstractParam<T> entityId, ApiEntity<T> entity) {
		return createDeleteResponse(entityId.get(), entity);
	}

	/**
	 * In other methods, path is derived from the input entity.  In this case, the input entity could be null (resulting
	 * in a NOT_FOUND response from the ResourceResponseFactory) so entityId must be provided separately.
	 */
	public <T> Response createDeleteResponse(T entityId, ApiEntity<T> entity) {
		return resourceResponseFactory.createDeleteResponse(pathFor(entityId), envelope(entity));
	}

	public Response createNotFoundResponse(Object entityId) {
		return resourceResponseFactory.createNotFoundResponse(pathFor(entityId));
	}

    public Response createConflictResponse(ApiEntity entity) {
	    return resourceResponseFactory.createConflictResponse(pathFor(entity));
    }

	public Response createSeeOtherResponse(ApiEntity entity) {
		return resourceResponseFactory.createSeeOtherResponse(pathFor(entity));
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
