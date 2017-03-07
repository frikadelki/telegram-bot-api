/*
 * ASH Toolset
 * Copyright 2017 ASH Dev Team
 * Created by ein on 2017/3/4
 */

package org.frikadelki.ash.toolset.utils.collections;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;


@UtilityClass
public final class CollectionsConvert {
	public static <T> ArrayList<T> listFromIterable(@NonNull final Iterable<? extends T> source) {
		final ArrayList<T> result = new ArrayList<>();
		for (final T t : source) {
			result.add(t);
		}
		return result;
	}

	public static <T> Iterable<T> iterableFromArray(final T[] items) {
		return new ArrayIterable<>(items);
	}
}
