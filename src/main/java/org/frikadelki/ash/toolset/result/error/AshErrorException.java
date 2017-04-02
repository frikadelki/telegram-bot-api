/*
 * ASH Toolset
 * Copyright 2017 ASH Dev Team
 * Created by ein on 2017/4/2
 */

package org.frikadelki.ash.toolset.result.error;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class AshErrorException extends Exception {
	@Getter @NonNull private final AshError error;
}
