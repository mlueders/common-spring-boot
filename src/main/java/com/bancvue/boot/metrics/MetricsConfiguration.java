package com.bancvue.boot.metrics;

import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableMetrics
@Configuration
public class MetricsConfiguration extends MetricsConfigurerAdapter {

	@Bean
	public MetricNamer metricNamer() {
		return new MetricNamer();
	}

	@Bean
	public CodahaleMetricsAdapter codahaleMetricsAdapter() {
		return new CodahaleMetricsAdapter();
	}

}