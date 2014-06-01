package com.bancvue.config;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

public abstract class AbstractServiceConfiguration {

	@Value("${service.uri.root}")
	String uriRoot;

	@Bean
	public ServletRegistrationBean jerseyServlet() {
		ServletContainer container = new ServletContainer();
		ServletRegistrationBean registration = new ServletRegistrationBean(container, uriRoot + "/*");

		registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, getJerseyConfigClass().getName());
		registration.addInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");
		registration.addInitParameter(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, "true");

		return registration;
	}

	protected abstract Class getJerseyConfigClass();

	@Bean
	Mapper getDozerBeanMapper() {
		return new DozerBeanMapper();
	}

}

