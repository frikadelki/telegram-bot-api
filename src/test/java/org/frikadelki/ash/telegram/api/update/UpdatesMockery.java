package org.frikadelki.ash.telegram.api.update;


import org.frikadelki.ash.telegram.api.message.TgmMessage;


final class UpdatesMockery {
	static TgmUpdate newMessageUpdate() {
		return newMessageUpdate(simpleMessage("Hello"));
	}

	static TgmUpdate newMessageUpdate(final TgmMessage message) {
		return TgmUpdate.builder().newMessage(message).build();
	}

	static TgmMessage simpleMessage(final String text) {
		return TgmMessage.builder().text(text).build();
	}
}
