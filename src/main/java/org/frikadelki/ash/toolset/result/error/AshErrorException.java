/*
 * ASH Toolset
 * Copyright 2017 ASH Dev Team
 * Created by ein on 2017/4/2
 */

package org.frikadelki.ash.toolset.result.error;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.IOException;


@RequiredArgsConstructor
public class AshErrorException extends Exception {
	@Getter @NonNull private final AshError error;

	public AshErrorException(@NonNull final IOException ioException) {
		error = AshCommonErrors.IO.error().code(-1).debugDescription(ioException.toString()).build();
	}

	public AshErrorException(@NonNull final RuntimeException runtimeException) {
		error = AshCommonErrors.RELEASE_ASSERTION.error().code(-1).debugDescription(runtimeException.toString()).build();
	}
}
