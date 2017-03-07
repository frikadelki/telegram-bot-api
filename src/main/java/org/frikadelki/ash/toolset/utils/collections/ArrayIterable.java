/*
 * ASH Toolset
 * Copyright 2017 ASH Dev Team
 * Created by ein on 2017/3/4
 */

package org.frikadelki.ash.toolset.utils.collections;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.frikadelki.ash.toolset.utils.AshAssert;


@RequiredArgsConstructor
public final class ArrayIterable<T> implements Iterable<T> {
	public static <T> java.util.Iterator<T> iterator(@NonNull final T[] items) {
		return new Iterator<>(items);
	}

	@NonNull private final T[] items;

	@Override
	public java.util.Iterator<T> iterator() {
		return iterator(items);
	}

	@RequiredArgsConstructor
	static final class Iterator<T> implements java.util.Iterator<T> {
		@NonNull private final T[] items;
		private int nextIndex = 0;

		@Override
		public boolean hasNext() {
			return (nextIndex < items.length);
		}

		@Override
		public T next() {
			AshAssert.aTrue(hasNext());
			if (!hasNext()) {
				return null;
			}
			return items[nextIndex++];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}

