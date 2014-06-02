package com.bancvue.boot.testsupport

import lombok.Getter
import org.glassfish.jersey.client.ClientConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.WebTarget

@Component
class ResourceIntegrationTestSupport {

	@Getter
	Client httpClient

	@Getter
	WebTarget resource

	@Value("\${server.port}")
	String port

	public ResourceIntegrationTestSupport() {
	}

	@PostConstruct
	public init() {
		System.setProperty("jersey.config.server.tracing", "ALL")

		ClientConfig clientConfig = new ClientConfig()
		httpClient = ClientBuilder.newClient(clientConfig)
		resource = httpClient.target("http://localhost:" + port + "/")
	}

	public String getUriAsString() {
		resource.uri.toString()
	}

	public close() {
		httpClient.close()
	}

}
