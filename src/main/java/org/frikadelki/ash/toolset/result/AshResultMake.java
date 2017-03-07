/*
 * ASH Toolset
 * Copyright 2017 ASH Dev Team
 * Created by ein on 2017/3/7
 */

package org.frikadelki.ash.toolset.result;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.frikadelki.ash.toolset.result.error.AshError;
import org.frikadelki.ash.toolset.utils.AshAssert;


@UtilityClass
public class AshResultMake {
	public static <T> AshResult<T> success() {
		return new AshResultSuccess<>(null);
	}

	public static <T> AshResult<T> success(final T data) {
		return new AshResultSuccess<>(data);
	}

	public static <T> AshResult<T> error(@NonNull final AshError error) {
		return new AshResultError<>(error);
	}
}

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AshResultSuccess<T> implements AshResult<T> {
	private final T data;

	@Override
	public boolean isSuccess() {
		return true;
	}

	@Override
	public T getData() {
		return data;
	}

	@Override
	public AshError getError() {
		AshAssert.fail("Not an error.");
		return null;
	}
}

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AshResultError<T> implements AshResult<T> {
	@NonNull
	private final AshError error;

	@Override
	public boolean isSuccess() {
		return false;
	}

	@Override
	public T getData() {
		AshAssert.fail("No data in error.");
		return null;
	}

	@Override
	public AshError getError() {
		return error;
	}
}
