/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/11/4
 */

package org.frikadelki.ash.telegram.api.update;


public interface TgmUpdateFilter {
	boolean isMatch(TgmUpdate update);
}
