package com.bancvue.boot.db.liquibase;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "liquibase")
public class LiquibaseSettings {

	private String changelog;
	private String contexts;
	private String operation;
}

