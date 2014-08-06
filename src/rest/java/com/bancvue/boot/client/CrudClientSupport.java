package com.bancvue.boot.client;

import com.bancvue.boot.api.ApiEntity;
import com.bancvue.boot.api.ApiValueObject;
import com.bancvue.rest.Envelope;
import com.bancvue.rest.client.ClientResponseFactory;
import com.bancvue.rest.client.CreateResponse;
import com.bancvue.rest.client.DeleteResponse;
import com.bancvue.rest.client.GetResponse;
import com.bancvue.rest.client.UpdateResponse;
import java.util.List;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

public class CrudClientSupport<T> {

	private WebTarget resource;
	private ClientResponseFactory clientResponseFactory;
	private GenericType<Envelope<T>> entityEnvelope;
	private GenericType<Envelope<List<T>>> entityListEnvelope;

	public CrudClientSupport(WebTarget resource, GenericType<Envelope<T>> entityEnvelope,
			GenericType<Envelope<List<T>>> entityListEnvelope) {
		this.resource = resource;
		this.entityEnvelope = entityEnvelope;
		this.entityListEnvelope = entityListEnvelope;
		this.clientResponseFactory = new ClientResponseFactory();
	}

	public WebTarget getResource() {
		return resource;
	}

	public ClientResponseFactory getClientResponseFactory() {
		return clientResponseFactory;
	}

	public CrudClientSupport<T> path(Object subpath) {
		return new CrudClientSupport<>(pathFor(subpath), entityEnvelope, entityListEnvelope);
	}

	public CrudClientSupport<T> queryParam(String name, Object... values) {
		return new CrudClientSupport<>(resource.queryParam(name, values), entityEnvelope, entityListEnvelope);
	}

	public WebTarget pathFor(Object segment) {
		if (segment instanceof ApiEntity) {
			segment = ((ApiEntity) segment).getId();
		} else if (segment instanceof ApiValueObject) {
			return resource;
		} else if (segment == null) {
			throw new IllegalStateException("Segment must not be null");
		}

		return resource.path(segment.toString());
	}

	public T find() {
		return find(resource);
	}

	public T find(Object path) {
		return find(pathFor(path));
	}

	public T find(WebTarget findResource) {
		GetResponse response = clientResponseFactory.get(findResource);
		return response.getResponseAsType(entityEnvelope).getData();
	}

	@Deprecated
	public List<T> findAll() {
		return findMany(resource);
	}

	public List<T> findMany() {
		return findMany(resource);
	}

	public List<T> findMany(WebTarget findManyResource) {
		GetResponse response = clientResponseFactory.get(findManyResource);
		return response.getResponseAsType(entityListEnvelope).getData();
	}

	public T insertWithPost(T entity) {
		return insertWithPost(resource, entity);
	}

	public T insertWithPost(WebTarget insertResource, T entity) {
		CreateResponse response = clientResponseFactory.createWithPost(insertResource, entity);
		return response.assertEntityCreatedAndGetResponse(entityEnvelope).getData();
	}

	public T updateWithPut(T entity) {
		return updateWithPut(pathFor(entity), entity);
	}

	public T updateWithPut(WebTarget updateResource, T entity) {
		UpdateResponse response = clientResponseFactory.updateWithPut(updateResource, entity);
		return response.assertEntityUpdatedAndGetResponse(entityEnvelope).getData();
	}

	public void delete() {
		delete(resource);
	}

	public void delete(Object path) {
		delete(pathFor(path));
	}

	public void delete(WebTarget deleteResource) {
		DeleteResponse response = clientResponseFactory.delete(deleteResource);
		response.assertEntityDeletedAndGetResponse(String.class);
	}

}
