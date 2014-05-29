package com.bancvue.boot.config;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

public class JerseyServiceConfiguration {

	@Value("${service.uri.root}")
	private String uriRoot;
	private String jerseyResourceConfigClassName;

	public JerseyServiceConfiguration(Class jerseyResourceConfigClass) {
		this.jerseyResourceConfigClassName = jerseyResourceConfigClass.getName();
	}

	@Bean
	public ServletRegistrationBean getServletRegistrationBean() {
		ServletContainer container = new ServletContainer();
		ServletRegistrationBean registration = new ServletRegistrationBean(container, uriRoot + "/*");

		registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, jerseyResourceConfigClassName);
		registration.addInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");
		registration.addInitParameter(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, "true");

		return registration;
	}

	@Bean
	Mapper getDozerBeanMapper() {
		return new DozerBeanMapper();
	}

}

