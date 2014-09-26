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
package com.bancvue.boot.db.liquibase;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ResourceAccessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.actuate.autoconfigure.MetricRepositoryAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Vector;

@Slf4j
@Configuration
@ComponentScan
@EnableAutoConfiguration(exclude = LiquibaseAutoConfiguration.class)
public class LiquibaseOperation implements CommandLineRunner {

	public static final String UPDATE = "update";


	@Autowired
	ResourceLoader resourceLoader;

	@Autowired
	DataSource dataSource;

	@Autowired
	LiquibaseSettings settings;


	Liquibase liquibase;


	@PostConstruct
	private void createLiquibaseInstance() {
		try {
			Database database = DatabaseFactory.getInstance()
					.findCorrectDatabaseImplementation(new JdbcConnection(dataSource.getConnection()));
			liquibase = new Liquibase(settings.getChangelog(), new SpringResourceOpener(), database);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	@Override
	public void run(String... args) throws Exception {
		try {
			if (settings.getOperation().equals(UPDATE)) {

				log.debug("Applying liquibase update");
				liquibase.update(settings.getContexts());
			} else {
				log.error("Unsupported operation: " + settings.getOperation());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	private class SpringResourceOpener implements ResourceAccessor {
		public InputStream getResourceAsStream(String file) throws IOException {
			try {
				Resource resource = getResource(file);
				return resource.getInputStream();
			}
			catch ( FileNotFoundException ex ) {
				return null;
			}
		}

		public Enumeration<URL> getResources(String packageName) throws IOException {
			Vector<URL> tmp = new Vector<>();

			tmp.add(getResource(packageName).getURL());

			return tmp.elements();
		}

		public Resource getResource(String file) {
			return resourceLoader.getResource(file);
		}

		public ClassLoader toClassLoader() {
			return resourceLoader.getClassLoader();
		}
	}
}
