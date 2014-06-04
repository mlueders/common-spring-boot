package com.bancvue.boot.config;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AnnotationUtils;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;

public class JerseyServiceConfiguration {

	private String jerseyServletContextRoot;

	private Class jerseyResourceConfigClass;

	public JerseyServiceConfiguration(Class jerseyResourceConfigClass) {
		this.jerseyResourceConfigClass = jerseyResourceConfigClass;
	}

	@PostConstruct
	public void initializeUriRoot() {
		jerseyServletContextRoot = findApplicationPath(AnnotationUtils.findAnnotation(jerseyResourceConfigClass,
				ApplicationPath.class));
	}

	@Bean
	public ServletRegistrationBean getServletRegistrationBean() {
		ServletContainer container = new ServletContainer();
		ServletRegistrationBean registration = new ServletRegistrationBean(container, jerseyServletContextRoot);

		registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, jerseyResourceConfigClass.getName());
		registration.addInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");
		registration.addInitParameter(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, "true");

		return registration;
	}

	@Bean
	Mapper getDozerBeanMapper() {
		return new DozerBeanMapper();
	}

	@Bean
	RequestScopedJerseyContext getRequestScopedJerseyContext() {
		return new RequestScopedJerseyContext();
	}

	private static String findApplicationPath(ApplicationPath annotation) {
		// Jersey doesn't like to be the default servlet, so map to /* as a fallback
		if (annotation == null) {
			return "/*";
		}
		String path = annotation.value();
		return path.isEmpty() || path.equals("/") ? "/*" : path + "/*";
	}

}

