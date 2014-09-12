package com.bancvue.boot.client;

import com.bancvue.boot.api.ApiEntity;
import com.bancvue.rest.client.request.ClientRequest;
import com.bancvue.rest.client.response.CreateResponse;
import com.bancvue.rest.client.response.DeleteResponse;
import com.bancvue.rest.client.response.GetResponse;
import com.bancvue.rest.client.response.UpdateResponse;
import com.bancvue.rest.envelope.DefaultEnvelope;
import java.util.List;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.GenericType;

/**
 * An adapter for {@link com.bancvue.rest.client.request.ClientRequest} which encapsulates invoking a request
 * and unwrapping the response.  It is expected that the target resource accepts and returns a single type (the
 * target type) and that responses are wrapped in a {@link com.bancvue.rest.envelope.DefaultEnvelope}.
 *
 * The constructor accepts the target resource type, which is then used to construct GenericType objects used
 * to deserialize and unwrap the response.  One limitation of this is that only simple, non-generic types are
 * supported.  If this limitation becomes problematic, there is a second, private constructor which allows
 * specifying the GenericType instances which will be used to deserialize the responses.
 */
public class CrudClientRequest<T> {

	private static final GenericTypeFactory GENERIC_TYPE_FACTORY = GenericTypeFactory.getInstance();

	private ClientRequest clientRequest;
	private GenericType<DefaultEnvelope<T>> entityEnvelope;
	private GenericType<DefaultEnvelope<List<T>>> entityListEnvelope;

	@SuppressWarnings("unchecked")
	public CrudClientRequest(ClientRequest clientRequest, Class<T> type) {
		this.clientRequest = clientRequest;
		this.entityEnvelope = GENERIC_TYPE_FACTORY.createGenericType(DefaultEnvelope.class, type);
		this.entityListEnvelope = GENERIC_TYPE_FACTORY.createGenericType(DefaultEnvelope.class, List.class, type);
	}

	private CrudClientRequest(ClientRequest clientRequest, GenericType<DefaultEnvelope<T>> entityEnvelope,
	                         GenericType<DefaultEnvelope<List<T>>> entityListEnvelope) {
		this.clientRequest = clientRequest;
		this.entityEnvelope = entityEnvelope;
		this.entityListEnvelope = entityListEnvelope;
	}

	public ClientRequest getClientRequest() {
		return clientRequest;
	}

	public CrudClientRequest<T> path(ApiEntity entity) {
		if (entity.getId() == null) {
			throw new IllegalArgumentException("Entity id must not be null");
		}

		return path(entity.getId());
	}

	public CrudClientRequest<T> path(Object segment) {
		if (segment == null) {
			throw new IllegalArgumentException("Segment must not be null");
		}

		String pathString = segment.toString();
		return createCrudClientRequest(clientRequest.path(pathString));
	}

	public CrudClientRequest<T> queryParam(String name, Object... values) {
		return createCrudClientRequest(clientRequest.queryParam(name, values));
	}

	public CrudClientRequest<T> header(String name, Object value) {
		return createCrudClientRequest(clientRequest.header(name, value));
	}

	public CrudClientRequest<T> property(String name, Object value) {
		return createCrudClientRequest(clientRequest.property(name, value));
	}

	public CrudClientRequest<T> cookie(String name, String value) {
		return createCrudClientRequest(clientRequest.cookie(name, value));
	}

	public CrudClientRequest<T> cookie(Cookie cookie) {
		return createCrudClientRequest(clientRequest.cookie(cookie));
	}

	private CrudClientRequest<T> createCrudClientRequest(ClientRequest clientRequest) {
		return new CrudClientRequest<>(clientRequest, entityEnvelope, entityListEnvelope);
	}


	/**
	 * Invokes HTTP GET method for the current request, returning a single entity.
	 */
	public T find() {
		GetResponse response = clientRequest.get();
		return response.getValidatedResponse(entityEnvelope).getData();
	}

	/**
	 * Invokes HTTP GET method for the current request, modifying the request path
	 * with the input path and returning a single entity.
	 */
	public T find(Object path) {
		return path(path).find();
	}

	/**
	 * Invokes HTTP GET method for the current request, returning a list of entities.
	 */
	public List<T> findMany() {
		GetResponse response = clientRequest.get();
		return response.getValidatedResponse(entityListEnvelope).getData();
	}

	/**
	 * Invokes HTTP POST method for the current request and the input entity.
	 */
	public T createWithPost(T entity) {
		CreateResponse response = clientRequest.createWithPost(entity);
		return response.getValidatedResponse(entityEnvelope).getData();
	}

	/**
	 * Invokes HTTP POST method for the given input entity, modifying the request
	 * path with the input path.
	 */
	public T createWithPost(Object path, T entity) {
		return path(path).createWithPost(entity);
	}

	/**
	 * Invokes HTTP PUT method for the current request and the input entity.
	 */
	public T updateWithPut(T entity) {
		UpdateResponse response = clientRequest.updateWithPut(entity);
		return response.getValidatedResponse(entityEnvelope).getData();
	}

	/**
	 * Invokes HTTP PUT method for the given input entity, modifying the request
	 * path with the input path.
	 */
	public T updateWithPut(Object path, T entity) {
		return path(path).updateWithPut(entity);
	}

	/**
	 * Invokes HTTP DELETE method for the current request.
	 */
	public void delete() {
		DeleteResponse response = clientRequest.delete();
		response.getValidatedResponse(String.class);
	}

	/**
	 * Invokes HTTP DELETE method for the current request, modifying the request path
	 * with the input path.
	 */
	public void delete(Object path) {
		path(path).delete();
	}

}
