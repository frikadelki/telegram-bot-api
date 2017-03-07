package org.frikadelki.ash.telegram.api.message;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.frikadelki.ash.telegram.api.chat.TgmChat;
import org.frikadelki.ash.telegram.api.chat.TgmChatTestData;

import java.util.Arrays;


@UtilityClass
public final class TgmMessageTestData {
	private static int NEXT_ID = 0;

	private static TgmMessage.Builder nextMessageBuilder(final TgmChat chat, final String text) {
		return TgmMessage.builder()
				.messageId(NEXT_ID++)
				.date(System.currentTimeMillis())
				.chat(null != chat ? chat : TgmChatTestData.any())
				.text(text);
	}

	public static TgmMessage withEntities(@NonNull final String text, @NonNull final TgmMessageEntity... entities) {
		return nextMessageBuilder(null, text)
				.entities(Arrays.asList(entities))
				.build();
	}
}
