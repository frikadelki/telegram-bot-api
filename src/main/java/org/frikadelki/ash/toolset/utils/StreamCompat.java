// ASH Toolset
// Copyright 2016 ASH Dev Team
// Created by ein on 2016/5/20

package org.frikadelki.ash.toolset.utils;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.Iterator;


public final class StreamCompat {
	public static <T> ArrayList<T> toList(@NonNull final Iterable<T> source) {
		final ArrayList<T> result = new ArrayList<>();
		for (final T t : source) {
			result.add(t);
		}

		return result;
	}

	public static <T> void forEach(@NonNull final Iterable<T> source, @NonNull final Lambda.Code1<T> action) {
		//noinspection Convert2streamapi
		for (final T t : source) {
			action.invoke(t);
		}
	}

	public static <T> boolean has(@NonNull final Iterable<T> source, @NonNull Lambda.FactoryCode1<Boolean, T> predicate) {
		return where(source, predicate).iterator().hasNext();
	}

	public static <T> T first(@NonNull final Iterable<T> source, @NonNull Lambda.FactoryCode1<Boolean, T> predicate) {
		final Iterator<T> whereIterator = where(source, predicate).iterator();
		final boolean hasNext = whereIterator.hasNext();
		if (!hasNext) {
			return null;
		}
		return whereIterator.next();
	}

	public static <W, T> Iterable<W> select(@NonNull final Iterable<T> source, @NonNull final Lambda.FactoryCode1<W, T> select) {
		return new Iterable<W>() {
			@Override
			public Iterator<W> iterator() {
				return new SelectIterator<>(source, select);
			}
		};
	}

	public static <T> Iterable<T> where(@NonNull final Iterable<T> source, @NonNull final Lambda.FactoryCode1<Boolean, T> where) {
		return new Iterable<T>() {
			@Override
			public Iterator<T> iterator() {
				return new WhereIterator<>(source, where);
			}
		};
	}

	private static class SelectIterator<W, T> implements Iterator<W> {
		private final Iterator<T> source;
		private final Lambda.FactoryCode1<W, T> select;

		SelectIterator(@NonNull final Iterable<T> source, @NonNull final Lambda.FactoryCode1<W, T> select) {
			this.source = source.iterator();
			this.select = select;
		}

		@Override
		public boolean hasNext() {
			return source.hasNext();
		}

		@Override
		public W next() {
			return select.produce(source.next());
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}


	private static class WhereIterator<T> implements Iterator<T> {
		private final Iterator<T> source;
		private final Lambda.FactoryCode1<Boolean, T> where;

		private T nextChecked = null;

		WhereIterator(@NonNull final Iterable<T> source, @NonNull final Lambda.FactoryCode1<Boolean, T> where) {
			this.source = source.iterator();
			this.where = where;
		}

		@Override
		public boolean hasNext() {
			return moveToNextCheckedIfAble();
		}

		@Override
		public T next() {
			final boolean hasNext = moveToNextCheckedIfAble();
			AshAssert.aTrue(hasNext);
			if (!hasNext) {
				return null;
			}
			final T next = nextChecked;
			nextChecked = null;
			return next;
		}

		private boolean moveToNextCheckedIfAble() {
			if (nextChecked != null) {
				return true;
			}
			while (source.hasNext()) {
				final T next = source.next();
				if (where.produce(next)) {
					nextChecked = next;
					break;
				}
			}
			return (nextChecked != null);
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}