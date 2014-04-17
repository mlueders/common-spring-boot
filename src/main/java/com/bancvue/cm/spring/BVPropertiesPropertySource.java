package com.bancvue.cm.spring;

import com.bancvue.cm.BVConfiguration;
import com.netflix.config.DynamicPropertyFactory;
import org.springframework.core.env.PropertySource;

public class BVPropertiesPropertySource extends PropertySource {

	private DynamicPropertyFactory dynamicPropertyFactory;

	public BVPropertiesPropertySource(String name) {
		super(name);
		try {
			BVConfiguration.configure();

			dynamicPropertyFactory = DynamicPropertyFactory.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	public BVPropertiesPropertySource() {
		this("bvprops");
	}

	@Override
	public Object getProperty(String name) {
		return dynamicPropertyFactory.getStringProperty(name, null).getValue();
	}
}
