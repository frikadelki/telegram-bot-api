package org.frikadelki.ash.telegram.api.chat.keyboard;

import lombok.NonNull;
import org.frikadelki.ash.telegram.api.keyboard.TgmKeyboardButton;
import org.frikadelki.ash.telegram.api.keyboard.TgmReplyKeyboardMarkup;
import java.util.List;


public interface TgmReplyKeyboardMarkupBuilder {
	KeyboardBuilder<TgmReplyKeyboardMarkupBuilder> startKeyboard();

	TgmReplyKeyboardMarkupBuilder keyboard(@NonNull final List<List<TgmKeyboardButton>> keyboard);

	TgmReplyKeyboardMarkupBuilder resizeKeyboard(final Boolean resizeKeyboard);

	TgmReplyKeyboardMarkupBuilder oneTimeKeyboard(final Boolean oneTimeKeyboard);

	TgmReplyKeyboardMarkupBuilder selective(final Boolean selective);

	TgmReplyKeyboardMarkup build();

	interface KeyboardBuilder<TMarkupBuilder> extends TgmKeyboardBuilder<KeyboardBuilder> {
		TMarkupBuilder endKeyboard();
	}
}
