/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/9
 */

package org.frikadelki.ash.toolset.result.error;


public interface AshLocalizer {
	CharSequence localize(final AshErrorDomain errorDomain, final long errorCode);
}
