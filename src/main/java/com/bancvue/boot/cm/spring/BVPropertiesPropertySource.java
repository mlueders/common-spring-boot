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
