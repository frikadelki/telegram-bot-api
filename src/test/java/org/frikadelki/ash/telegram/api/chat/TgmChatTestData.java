package org.frikadelki.ash.telegram.api.chat;


import lombok.experimental.UtilityClass;


@UtilityClass
public final class TgmChatTestData {
	public static TgmChat any() {
		return TgmChat.builder()
				.id(1)
				.type(TgmChat.Type.PRIVATE)
				.build();
	}
}
