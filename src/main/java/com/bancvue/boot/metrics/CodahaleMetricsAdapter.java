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

import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Meter;
import com.codahale.metrics.Metered;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Snapshot;
import com.codahale.metrics.Timer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.stereotype.Component;

@Component
public class CodahaleMetricsAdapter implements PublicMetrics {

	@Autowired
	private MetricNamer metricNamer;
	@Autowired
	private MetricRegistry metricRegistry;

	@Override
	public Collection<Metric<?>> metrics() {
		MetricsCollector metrics = new MetricsCollector(metricNamer, metricRegistry);
		metrics.collectTimers();
		metrics.collectMeters();
		metrics.collectGauges();
		metrics.collectCounters();
		return metrics.getMetrics();
	}


	private static final class MetricsCollector {

		private MetricNamer namer;
		private MetricRegistry registry;
		private ArrayList<Metric<?>> metrics = new ArrayList<>();

		private MetricsCollector(MetricNamer namer, MetricRegistry registry) {
			this.namer = namer;
			this.registry = registry;
		}

		public ArrayList<Metric<?>> getMetrics() {
			return metrics;
		}

		public void collectTimers() {
			SortedMap<String, Timer> timers = registry.getTimers();
			for (String key : timers.keySet()) {
				Timer timer = timers.get(key);

				if ((timer != null) && (timer.getCount() > 0)) {
					collectTimerMetrics(key, timer);
				}
			}
		}

		private void collectTimerMetrics(String key, Timer timer) {
			collectMeterMetrics(key, timer);

			Snapshot snapshot = timer.getSnapshot();
			addMetric("timer.min", key, snapshot.getMin());
			addMetric("timer.max", key, snapshot.getMax());
			addMetric("timer.median", key, snapshot.getMedian());
			addMetric("timer.mean", key, snapshot.getMean());
			addMetric("timer.standard-deviation", key, snapshot.getStdDev());
		}

		public void collectMeters() {
			SortedMap<String, Meter> meters = registry.getMeters();
			for (String key : meters.keySet()) {
				Meter meter = meters.get(key);

				if ((meter != null) && (meter.getCount() > 0)) {
					collectMeterMetrics(key, meter);
				}
			}
		}

		private void collectMeterMetrics(String key, Metered meter) {
			addMetric("meter.mean", key, meter.getMeanRate());
			addMetric("meter.one-minute", key, meter.getOneMinuteRate());
			addMetric("meter.five-minute", key, meter.getFiveMinuteRate());
			addMetric("meter.fifteen-minute", key, meter.getFifteenMinuteRate());
		}

		public void collectGauges() {
			SortedMap<String, Gauge> gauges = registry.getGauges();
			for (String key : gauges.keySet()) {
				Gauge gauge = gauges.get(key);

				if ((gauge != null) && (gauge.getValue() instanceof Number)) {
					addMetric("gauge", key, (Number) gauge.getValue());
				}
			}
		}

		public void collectCounters() {
			SortedMap<String, Counter> counters = registry.getCounters();
			for (String key : counters.keySet()) {
				Counter counter = counters.get(key);

				if (counter != null) {
					addMetric("counter", key, counter.getCount());
				}
			}
		}

		private <T extends Number> void addMetric(String metricType, String key, T metric) {
			String name = namer.getMetricName(metricType, key);
			metrics.add(new Metric<>(name, metric));
		}

	}

}
