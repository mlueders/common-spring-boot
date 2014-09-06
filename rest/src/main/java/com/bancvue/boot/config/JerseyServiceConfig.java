package com.bancvue.boot.config;

import com.bancvue.boot.metrics.MetricRequestFilter;
import com.bancvue.boot.metrics.MetricsConfiguration;
import com.bancvue.rest.client.WebTargetFactory;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.client.Client;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AnnotationUtils;

@Import(MetricsConfiguration.class)
public class JerseyServiceConfig {

	private String jerseyServletContextRoot;

	private Class jerseyResourceConfigClass;

	public JerseyServiceConfig(Class jerseyResourceConfigClass) {
		this.jerseyResourceConfigClass = jerseyResourceConfigClass;
	}

	@PostConstruct
	public void initializeUriRoot() {
		jerseyServletContextRoot = resolveApplicationPathFromAnnotation();
	}

	private String resolveApplicationPathFromAnnotation() {
		ApplicationPath annotation = AnnotationUtils.findAnnotation(jerseyResourceConfigClass, ApplicationPath.class);
		if (annotation == null) {
			throw new ApplicationPathAnnotationMissingException(jerseyResourceConfigClass);
		}

		return resolveApplicationPath(annotation.value());
	}

	/**
	 * Jersey does not play well with Spring if the jersey application path is / or /*.  The effect is that
	 * exceptions get translated to 404 responses (on exception, the server tries to POST to /error which spring
	 * is aware of but Jersey ends up handling, resulting in a 404 back to the client).  So, fail fast if there is
	 * no context root.
	 */
	private String resolveApplicationPath(String applicationPath) {
		if (!Pattern.matches("^/\\w.*", applicationPath)) {
			throw new InvalidApplicationPathException(applicationPath);
		}

		if (!applicationPath.endsWith("/*")) {
			applicationPath += applicationPath.endsWith("/") ? "*" : "/*";
		}
		return applicationPath;
	}

	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		ServletContainer container = new ServletContainer();
		ServletRegistrationBean registration = new ServletRegistrationBean(container, jerseyServletContextRoot);

		registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, jerseyResourceConfigClass.getName());
		registration.addInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");
		registration.addInitParameter(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, "true");

		return registration;
	}

	@Bean
	public Mapper dozerBeanMapper() {
		return new DozerBeanMapper();
	}

	@Bean
	public RequestScopedJerseyContext requestScopedJerseyContext() {
		return new RequestScopedJerseyContext();
	}

	@Bean
	public MetricRequestFilter metricRequestFilter() {
		return new MetricRequestFilter();
	}

	@Bean
	public Client jaxrsClient() {
		// TODO: need to read configuration properties and initialize client
		return WebTargetFactory.createDefaultJaxrsClient();
	}


	public static final class ApplicationPathAnnotationMissingException extends RuntimeException {
		public ApplicationPathAnnotationMissingException(Class configClass) {
			super("Jersey configuration=" + configClass + " must be annotated with " + ApplicationPath.class.getName());
		}
	}

	public static final class InvalidApplicationPathException extends RuntimeException {
		public InvalidApplicationPathException(String path) {
			super("Invalid application path=" + path);
		}
	}

}

