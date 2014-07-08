package com.bancvue.boot.db.repository;

import com.bancvue.boot.api.ApiEntity;
import com.bancvue.boot.mapping.ListMapper;
import java.util.List;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractApiRepository<API_ENTITY_TYPE extends ApiEntity, BACKING_ENTITY_TYPE> {

	@Autowired
	private Mapper mapper;
	private Class<API_ENTITY_TYPE> apiEntityType;
	private Class<BACKING_ENTITY_TYPE> backingEntityType;

	protected AbstractApiRepository(Class<API_ENTITY_TYPE> apiEntityType, Class<BACKING_ENTITY_TYPE> backingEntityType) {
		this.apiEntityType = apiEntityType;
		this.backingEntityType = backingEntityType;
	}

	protected List<BACKING_ENTITY_TYPE> toBackingList(List<API_ENTITY_TYPE> source) {
		return ListMapper.map(mapper, source, backingEntityType);
	}

	protected List<API_ENTITY_TYPE> toApiList(List<BACKING_ENTITY_TYPE> source) {
		return ListMapper.map(mapper, source, apiEntityType);
	}

	protected API_ENTITY_TYPE toApiEntity(BACKING_ENTITY_TYPE entity) {
		return mapIfNotNull(entity, apiEntityType);
	}

	protected BACKING_ENTITY_TYPE toBackingEntity(API_ENTITY_TYPE entity) {
		return mapIfNotNull(entity, backingEntityType);
	}

	private <T, U> U mapIfNotNull(T source, Class<U> destType) {
		return source != null ? mapper.map(source, destType) : null;
	}

}
