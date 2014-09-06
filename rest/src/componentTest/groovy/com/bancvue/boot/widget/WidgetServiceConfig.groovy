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
