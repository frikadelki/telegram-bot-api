/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/18
 */

package org.frikadelki.ash.telegram.api.chat;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import org.frikadelki.ash.telegram.api.base.TgmEntity;


public final class TgmUser {
	@Getter private long id = TgmEntity.INVALID_ID;

	@SerializedName("first_name")
	@Getter private String firstName;
	@SerializedName("last_name")
	@Getter private String lastName;

	@Getter private String username;
}
