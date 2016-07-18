// ASH Toolset
// Copyright 2016 ASH Dev Team
// Created by ein on 2016/5/20

package org.frikadelki.ash.toolset.utils;


public final class Lambda {

	public interface Code0 {
		void invoke();
	}

	public interface Code0Ex {
		void invoke() throws Exception;
	}

	public static void safeCall(final Code0 delegate) {
		if (null == delegate) {
			return;
		}
		delegate.invoke();
	}

	public interface Code1<T> {
		void invoke(T argument);
	}

	public interface Code1Ex<T> {
		void invoke(T argument) throws Exception;
	}

	public static <T> void safeCall(final Code1<T> delegate, final T arg) {
		if (null == delegate) {
			return;
		}
		delegate.invoke(arg);
	}

	public interface Code2<T, K> {
		void invoke(T tArgument, K kArgument);
	}

	public static <T, K> void safeCall(final Code2<T, K> delegate, final T tArg, final K kArg) {
		if (null == delegate) {
			return;
		}
		delegate.invoke(tArg, kArg);
	}

	public interface FactoryCode0<W> {
		W produce();
	}

	public interface FactoryCode1<W, T> {
		W produce(final T t);
	}

	public interface FactoryCode2<W, T, K> {
		W produce(final T t, final K k);
	}
}
