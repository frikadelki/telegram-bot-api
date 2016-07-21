/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/18
 */

package org.frikadelki.ash.telegram.api.chat;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import lombok.NonNull;
import org.frikadelki.ash.telegram.api.base.TgmEntity;


public final class TgmChat {
	public enum Type {
		@SerializedName("private") 		PRIVATE,
		@SerializedName("group") 		GROUP,
		@SerializedName("supergroup") 	SUPERGROUP,
		@SerializedName("channel") 		CHANNEL,
		UNKNOWN,
		;
	}

	@Getter private long id = TgmEntity.INVALID_ID;
	@Getter private Type type = null;

	public boolean is(@NonNull final Type type) {
		return (this.type == type);
	}

	/**
	 * Optional. Title, for channels and group chats
	 */
	@Getter private String title = null;

	/**
	 * Optional. Username, for private chats, supergroups and channels if available
	 */
	@Getter private String username = null;

	/**
	 * Optional. First name of the other party in a private chat
	 */
	@SerializedName("first_name")
	@Getter private String firstName = null;

	/**
	 * Optional. Last name of the other party in a private chat
	 */
	@SerializedName("last_name")
	@Getter private String lastName = null;
}
