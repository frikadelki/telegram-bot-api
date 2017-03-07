/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/18
 */

package org.frikadelki.ash.telegram.api.chat;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import org.frikadelki.ash.telegram.api.base.TgmEntity;


@Builder(builderClassName = "Builder") // for unit tests
@AllArgsConstructor(access = AccessLevel.PACKAGE) // for the above builder
@NoArgsConstructor(access = AccessLevel.PRIVATE) // for GSON
public final class TgmChat {
	public enum Type {
		@SerializedName("private") 		PRIVATE,
		@SerializedName("group") 		GROUP,
		@SerializedName("supergroup") 	SUPERGROUP,
		@SerializedName("channel") 		CHANNEL,
		UNKNOWN,
		;
	}

	/**
	 * Required.
	 */
	@Getter private long id = TgmEntity.INVALID_ID;

	/**
	 * Required.
	 */
	@Getter private Type type;

	/**
	 * Optional. Title, for channels and group chats
	 */
	@Getter private String title;

	/**
	 * Optional. Username, for private chats, supergroups and channels if available
	 */
	@Getter private String username;

	/**
	 * Optional. First name of the other party in a private chat
	 */
	@SerializedName("first_name")
	@Getter private String firstName;

	/**
	 * Optional. Last name of the other party in a private chat
	 */
	@SerializedName("last_name")
	@Getter private String lastName;

	public boolean is(@NonNull final Type type) {
		return (this.type == type);
	}
}
