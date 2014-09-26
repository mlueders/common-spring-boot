/*
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
package com.bancvue.boot.application

import com.bancvue.cm.BVConfiguration
import org.springframework.context.ConfigurableApplicationContext
import spock.lang.Specification;


public class BVSpringApplicationSpec extends Specification {

	def setup(){
		System.setProperty(BVConfiguration.CONFIG, "src/componentTest/resources/componentTest.properties")
	}

	def cleanup(){
		System.getProperties().remove(BVConfiguration.CONFIG)	
	}
	
	def "should load Application Context"(){
		given:
		BVSpringApplication bvSpringApplication = new BVSpringApplication(SimpleTestComponentConfig.class);
		
		when:
		ConfigurableApplicationContext applicationContext = bvSpringApplication.run("hello")
		
		then:
		applicationContext.getBean("hello")
		
		cleanup:
		applicationContext?.close()

	}
}
