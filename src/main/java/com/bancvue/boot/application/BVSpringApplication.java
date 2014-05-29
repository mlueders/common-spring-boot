package com.bancvue.boot.application;

import com.bancvue.boot.cm.spring.PropertySourceInitializer;
import com.bancvue.boot.db.liquibase.LiquibaseOperation;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;

import java.util.Arrays;

@Slf4j
public class BVSpringApplication {

	private Class applicationClass;

	@Setter
	private boolean showBanner = false;

	public BVSpringApplication(Class applicationClass) {
		this.applicationClass = applicationClass;
	}

	public void run(String[] args) {
		SpringApplication app;

		if (Arrays.asList(args).contains("db")) {
			log.info("Starting liquibase operation");
			app = new SpringApplication(LiquibaseOperation.class);
			app.setWebEnvironment(false);
		} else {
			log.info("Starting application");
			app = new SpringApplication(applicationClass);
		}

		app.setShowBanner(showBanner);
		app.addInitializers(new PropertySourceInitializer());
		app.run(args);

	}
}
