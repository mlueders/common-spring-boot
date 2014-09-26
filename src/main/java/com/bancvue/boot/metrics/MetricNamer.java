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
