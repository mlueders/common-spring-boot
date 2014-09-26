/*
 * Copyright 2014 ${name}
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
package com.bancvue.boot.widget

import com.bancvue.boot.config.JerseyResourceConfig
import com.bancvue.boot.config.JerseyServiceConfig
import javax.ws.rs.ApplicationPath
import org.springframework.boot.actuate.autoconfigure.MetricRepositoryAutoConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@EnableAutoConfiguration(exclude = [DataSourceAutoConfiguration.class, LiquibaseAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class, MetricRepositoryAutoConfiguration.class])
@ComponentScan(basePackages = ["com.bancvue.boot.widget"])
public class WidgetServiceConfig extends JerseyServiceConfig {

	public WidgetServiceConfig() {
		super(JerseyConfig.class);
	}


	@ApplicationPath("/root")
	public static class JerseyConfig extends JerseyResourceConfig {

		@Override
		protected String[] getPackagesToAutoScan() {
			["com.bancvue.boot.widget"]
		}
	}

}
