// ASH Toolset
// Copyright 2016 ASH Dev Team
// Created by ein on 2016/5/20

package org.frikadelki.ash.toolset.utils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import lombok.val;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;


@UtilityClass
public final class StreamCompat {
	public static <T> ArrayList<T> toList(@NonNull final Iterable<? extends T> source) {
		final ArrayList<T> result = new ArrayList<>();
		for (final T t : source) {
			result.add(t);
		}

		return result;
	}

	public static <T> void forEach(@NonNull final Iterable<? extends T> source, @NonNull final Lambda.Code1<T> action) {
		//noinspection Convert2streamapi
		for (final T t : source) {
			action.invoke(t);
		}
	}

	public static <T> boolean has(@NonNull final Iterable<? extends T> source, @NonNull final Lambda.Predicate1<T> predicate) {
		return where(source, predicate).iterator().hasNext();
	}

	public static <T> boolean has(@NonNull final Iterable<? extends T> source, @NonNull final T exactMatch) {
		return where(source, new Lambda.Predicate1<T>() {
			@Override
			public Boolean produce(final T t) {
				return (exactMatch == t);
			}
		}).iterator().hasNext();
	}

	public static <T> T first(@NonNull final Iterable<? extends T> source, @NonNull Lambda.Predicate1<T> predicate) {
		final Iterator<T> whereIterator = where(source, predicate).iterator();
		final boolean hasNext = whereIterator.hasNext();
		if (!hasNext) {
			return null;
		}
		return whereIterator.next();
	}

	public static <W, T> Iterable<W> select(@NonNull final Iterable<? extends T> source, @NonNull final Lambda.FactoryCode1<W, T> select) {
		return new Iterable<W>() {
			@Override
			public Iterator<W> iterator() {
				return new SelectIterator<>(source.iterator(), select);
			}
		};
	}

	public static <T> Iterable<T> where(@NonNull final Iterable<? extends T> source, @NonNull final Lambda.Predicate1<T> where) {
		return new Iterable<T>() {
			@Override
			public Iterator<T> iterator() {
				return new WhereIterator<>(source.iterator(), where);
			}
		};
	}

	public static <T> Iterable<T> concat(@NonNull final Iterable<Iterable<T>> sources) {
		return new Iterable<T>() {
			@Override
			public Iterator<T> iterator() {
				return new ConcatIterator<>(sources.iterator());
			}
		};
	}

	@RequiredArgsConstructor
	private static class SelectIterator<W, T> implements Iterator<W> {
		@NonNull private final Iterator<? extends T> source;
		@NonNull private final Lambda.FactoryCode1<W, T> select;

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

	@RequiredArgsConstructor
	private static class WhereIterator<T> implements Iterator<T> {
		@NonNull private final Iterator<? extends T> source;
		@NonNull private final Lambda.Predicate1<T> where;

		private T nextChecked = null;

		@Override
		public boolean hasNext() {
			return moveToNextCheckedIfAble();
		}

		@Override
		public T next() {
			final boolean hasNext = moveToNextCheckedIfAble();
			if (!hasNext) {
				throw new NoSuchElementException();
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

	@RequiredArgsConstructor
	private static class ConcatIterator<T> implements Iterator<T> {
		@NonNull private final Iterator<Iterable<T>> source;

		private Iterator<T> currentIterator = null;

		@Override
		public boolean hasNext() {
			if ((null != currentIterator) && currentIterator.hasNext()) {
				return true;
			}

			while (source.hasNext()) {
				val nextIterator = source.next().iterator();
				if (nextIterator.hasNext()) {
					currentIterator = nextIterator;
					return true;
				}
			}

			return false;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return currentIterator.next();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}