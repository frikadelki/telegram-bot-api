package org.frikadelki.ash.telegram.api.chat.keyboard;

import org.frikadelki.ash.telegram.api.keyboard.TgmKeyboardButton;
import java.util.List;


public interface TgmKeyboardBuilder<TSelf extends TgmKeyboardBuilder> {

	KeyboardRowBuilder<TSelf> startRow();

	TSelf row(final List<TgmKeyboardButton> buttonsRow);

	List<List<TgmKeyboardButton>> build();

	interface KeyboardRowBuilder<TKeyboardBuilder> extends TgmKeyboardRowBuilder<KeyboardRowBuilder> {
		TKeyboardBuilder endRow();
	}
}
