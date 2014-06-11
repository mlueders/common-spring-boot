package com.bancvue.boot.client;

import com.bancvue.boot.api.ApiEntity;
import com.bancvue.rest.Envelope;
import com.bancvue.rest.client.ClientResponseFactory;
import com.bancvue.rest.client.CreateResponse;
import com.bancvue.rest.client.GetResponse;
import com.bancvue.rest.client.UpdateResponse;
import java.util.List;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

public class CrudClientSupport<T extends ApiEntity> {

	private WebTarget resource;
	private ClientResponseFactory clientResponseFactory;
	private GenericType<Envelope<T>> entityEnvelope;
	private GenericType<Envelope<List<T>>> entityListEnvelope;

	public CrudClientSupport(WebTarget resource, GenericType<Envelope<T>> entityEnvelope, GenericType<Envelope<List<T>>> entityListEnvelope) {
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

	public WebTarget path(Long id) {
		return resource.path(Long.toString(id));
	}

	public WebTarget path(ApiEntity entity) {
		return path(entity.getId());
	}

	public T find(Long id) {
		return find(path(id));
	}

	public T find(WebTarget findResource) {
		GetResponse response = clientResponseFactory.get(findResource);
		return response.getResponseAsType(entityEnvelope).getData();
	}

	public List<T> findAll() {
		return findMany(resource);
	}

	public List<T> findMany(WebTarget findManyResource) {
		GetResponse response = clientResponseFactory.get(findManyResource);
		return response.getResponseAsType(entityListEnvelope).getData();
	}

	public T insertWithPost(T entity) {
		CreateResponse response = clientResponseFactory.createWithPost(resource, entity);
		return response.assertEntityCreatedAndGetResponse(entityEnvelope).getData();
	}

	public void updateWithPut(T entity) {
		UpdateResponse response = clientResponseFactory.updateWithPut(path(entity), entity);
		response.assertEntityUpdatedAndGetResponse(entityEnvelope).getData();
	}

	public void delete(T entity) {
		clientResponseFactory.delete(path(entity)).assertEntityDeletedAndGetResponse(String.class);
	}

}
