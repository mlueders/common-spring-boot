/**
 * Copyright 2014 BancVue, LTD
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
package com.bancvue.boot.application;

import com.bancvue.boot.cm.spring.PropertySourceInitializer;
import com.bancvue.boot.db.liquibase.LiquibaseOperation;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

@Slf4j
public class BVSpringApplication {

	private Class[] applicationClasses;

	@Setter
	private boolean showBanner = false;

	public BVSpringApplication(Class... applicationClasses) {
		this.applicationClasses = applicationClasses;
	}

	public ConfigurableApplicationContext run(String[] args) {
		SpringApplication app;

		if (Arrays.asList(args).contains("db")) {
			log.info("Starting liquibase operation");
			app = new SpringApplication(LiquibaseOperation.class);
			app.setWebEnvironment(false);
		} else {
			log.info("Starting application");
			app = new SpringApplication((Object[]) applicationClasses);
		}

		app.setShowBanner(showBanner);
		app.addInitializers(new PropertySourceInitializer());
		return app.run(args);
	}
}
