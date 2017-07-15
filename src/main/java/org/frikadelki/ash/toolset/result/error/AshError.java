/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/9
 */

package org.frikadelki.ash.toolset.result.error;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;


@Builder(builderClassName = "Builder")
@ToString(of = {"errorDomain", "code", "debugDescription"})
public final class AshError {
	@Getter @NonNull private final AshErrorDomain errorDomain;
	@Getter private final long code;
	private final AshLocalizer localizer;

	private final String debugDescription;

	public boolean is(@NonNull final AshErrorDomain errorDomain) {
		return this.errorDomain.equals(errorDomain);
	}

	public CharSequence tryLocalize() {
		if (null == localizer) {
			return null;
		}
		return localizer.localize(errorDomain, code);
	}
}