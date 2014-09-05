package com.bancvue.boot.jackson.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

@Provider
public class JodaTimeObjectMapperContextResolver implements ContextResolver<ObjectMapper> {
	@Override
	public ObjectMapper getContext(Class<?> type) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(WRITE_DATES_AS_TIMESTAMPS , false);
		mapper.registerModule(new JodaModule());
		return mapper;
	}
}
