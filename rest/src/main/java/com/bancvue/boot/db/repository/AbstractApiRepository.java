/**
 * Copyright 2014 ${name}
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bancvue.boot.db.repository;

import com.bancvue.boot.mapping.ListMapper;
import java.util.List;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractApiRepository<API_ENTITY_TYPE, BACKING_ENTITY_TYPE> {

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
