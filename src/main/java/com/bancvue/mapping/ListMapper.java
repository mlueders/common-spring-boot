package com.bancvue.mapping;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.dozer.Mapper;

import java.util.List;

public class ListMapper {

	public static <T, U> List<U> map(final Mapper mapper, final List<T> source, final Class<U> destType) {

		return Lists.newArrayList(Iterables.transform(source, new Function<T, U>() {
			@Override
			public U apply(final T arg) {
				return mapper.map(arg, destType);
			}
		}));
	}
}