package com.bancvue.cm.spring;

import com.bancvue.cm.BVProperties;
import org.springframework.core.env.PropertySource;

public class BVPropertiesPropertySource extends PropertySource {

	private BVProperties bvProperties;

	public BVPropertiesPropertySource(String name) {
		super(name);
		bvProperties = new BVProperties();
	}

	public BVPropertiesPropertySource() {
		this("bvprops");
	}

	@Override
	public Object getProperty(String name) {
		return bvProperties.getString(name);
	}
}
