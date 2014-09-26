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
package com.bancvue.boot.mapping;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.dozer.Mapper;

import java.util.List;

public class ListMapper {

	public static <T, U> List<U> map(final Mapper mapper, final Iterable<T> source, final Class<U> destType) {

		return Lists.newArrayList(Iterables.transform(source, new Function<T, U>() {
			@Override
			public U apply(final T arg) {
				return mapper.map(arg, destType);
			}
		}));
	}
}