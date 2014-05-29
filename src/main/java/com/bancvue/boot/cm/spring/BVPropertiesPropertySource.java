package com.bancvue.boot.cm.spring;

import com.bancvue.cm.BVProperties;
import org.springframework.core.env.PropertySource;

public class BVPropertiesPropertySource extends PropertySource {

	private static final String DEFAULT_PROPERTY_SOURCE_NAME = "bvprops";
	private BVProperties bvProperties;

	public BVPropertiesPropertySource(String name) {
		super(name);
		bvProperties = new BVProperties();
	}

	public BVPropertiesPropertySource() {
		this(DEFAULT_PROPERTY_SOURCE_NAME);
	}

	@Override
	public Object getProperty(String name) {
		return bvProperties.getString(name);
	}
}
