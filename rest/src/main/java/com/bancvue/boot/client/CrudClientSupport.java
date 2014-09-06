package com.bancvue.boot.client;

import com.bancvue.boot.api.ApiEntity;
import com.bancvue.rest.client.ClientRequest;
import com.bancvue.rest.client.response.CreateResponse;
import com.bancvue.rest.client.response.DeleteResponse;
import com.bancvue.rest.client.response.GetResponse;
import com.bancvue.rest.client.response.UpdateResponse;
import com.bancvue.rest.envelope.DefaultEnvelope;
import java.util.List;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.GenericType;

public class CrudClientSupport<T> {

	private ClientRequest clientRequest;
	private GenericType<DefaultEnvelope<T>> entityEnvelope;
	private GenericType<DefaultEnvelope<List<T>>> entityListEnvelope;

	public CrudClientSupport(ClientRequest clientRequest, GenericType<DefaultEnvelope<T>> entityEnvelope,
	                         GenericType<DefaultEnvelope<List<T>>> entityListEnvelope) {
		this.entityEnvelope = entityEnvelope;
		this.entityListEnvelope = entityListEnvelope;
		this.clientRequest = clientRequest;
	}

	public ClientRequest getClientRequest() {
		return clientRequest;
	}

	public CrudClientSupport<T> path(ApiEntity entity) {
		if (entity.getId() == null) {
			throw new IllegalArgumentException("Entity id must not be null");
		}

		return path(entity.getId());
	}

	public CrudClientSupport<T> path(Object segment) {
		if (segment == null) {
			throw new IllegalArgumentException("Segment must not be null");
		}

		String pathString = segment.toString();
		return createCrudClientRequest(clientRequest.path(pathString));
	}

	public CrudClientSupport<T> queryParam(String name, Object... values) {
		return createCrudClientRequest(clientRequest.queryParam(name, values));
	}

	public CrudClientSupport<T> header(String name, Object value) {
		return createCrudClientRequest(clientRequest.header(name, value));
	}

	public CrudClientSupport<T> property(String name, Object value) {
		return createCrudClientRequest(clientRequest.property(name, value));
	}

	public CrudClientSupport<T> cookie(String name, String value) {
		return createCrudClientRequest(clientRequest.cookie(name, value));
	}

	public CrudClientSupport<T> cookie(Cookie cookie) {
		return createCrudClientRequest(clientRequest.cookie(cookie));
	}

	private CrudClientSupport<T> createCrudClientRequest(ClientRequest clientRequest) {
		return new CrudClientSupport<>(clientRequest, entityEnvelope, entityListEnvelope);
	}


	public T find() {
		GetResponse response = clientRequest.get();
		return response.getValidatedResponse(entityEnvelope).getData();
	}

	public T find(Object path) {
		return path(path).find();
	}

	public List<T> findMany() {
		GetResponse response = clientRequest.get();
		return response.getValidatedResponse(entityListEnvelope).getData();
	}

	public T insertWithPost(T entity) {
		CreateResponse response = clientRequest.createWithPost(entity);
		return response.getValidatedResponse(entityEnvelope).getData();
	}

	public T updateWithPut(T entity) {
		UpdateResponse response = clientRequest.updateWithPut(entity);
		return response.getValidatedResponse(entityEnvelope).getData();
	}

	public T updateEntityWithPut(ApiEntity entity) {
		return path(entity).updateWithPut((T) entity);
	}

	public void delete() {
		DeleteResponse response = clientRequest.delete();
		response.getValidatedResponse(String.class);
	}

	public void delete(Object path) {
		path(path).delete();
	}

}
