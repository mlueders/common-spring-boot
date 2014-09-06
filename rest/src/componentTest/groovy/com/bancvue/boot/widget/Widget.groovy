package com.bancvue.boot.widget

import com.bancvue.boot.api.ApiEntity
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Widget implements ApiEntity<Long> {

	Long id = 5

	Widget() {
	}

	Widget(Long id) {
		this.id = id
	}

	String toString() {
		"Widget[${id}]"
	}
}
