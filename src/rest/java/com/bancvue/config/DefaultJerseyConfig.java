package com.bancvue.config;

import com.bancvue.exception.mapper.InvalidEntityExceptionMapper;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

public class DefaultJerseyConfig extends ResourceConfig {

	protected DefaultJerseyConfig() {
		property(ServerProperties.JSON_PROCESSING_FEATURE_DISABLE, false);
		property(ServerProperties.MOXY_JSON_FEATURE_DISABLE, true);
		property(ServerProperties.WADL_FEATURE_DISABLE, true);
		register(LoggingFilter.class);
		register(InvalidEntityExceptionMapper.class);
	}

}
