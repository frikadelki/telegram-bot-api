/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/18
 */

package org.frikadelki.ash.telegram.api.base;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;


public final class TgmResponse {
	@Getter private boolean ok = false;

	@SerializedName("error_code")
	@Getter private long errorCode = 0;
	@Getter private String description = null;

	@Getter private JsonElement result = null;
}
