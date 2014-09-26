/**
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
package com.bancvue.boot.metrics;

import com.codahale.metrics.MetricRegistry;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MetricRequestFilter extends OncePerRequestFilter {

	private static final String UNDEFINED_HTTP_STATUS = "999";

	@Autowired
	private MetricRegistry metricRegistry;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
	                                FilterChain chain) throws ServletException, IOException {
		try {
			chain.doFilter(request, response);
		} finally {
			String key = "status." + getStatus(response);
			metricRegistry.counter(key).inc();
		}
	}

	private String getStatus(HttpServletResponse response) {
		try {
			return Integer.toString(response.getStatus());
		} catch (Exception ex) {
			return UNDEFINED_HTTP_STATUS;
		}
	}

}
