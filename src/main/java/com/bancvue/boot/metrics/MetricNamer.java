package com.bancvue.boot.metrics;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MetricNamer {

	@Value("${service.name:unknown}")
	private String serviceName;

	public String getMetricName(String metricType, String metricKey) {
		String shortKeyName = convertKeyToShortName(metricKey);
		return serviceName + "." + metricType + "." + shortKeyName;
	}

	private String convertKeyToShortName(String key) {
		String shortKeyName = key;

		if (key.startsWith("com.bancvue")) {
			String[] segments = key.split("\\.");
			if (segments.length > 1) {
				shortKeyName = segments[segments.length - 2] + "." + segments[segments.length - 1];
			}
		}
		return shortKeyName;
	}

}
