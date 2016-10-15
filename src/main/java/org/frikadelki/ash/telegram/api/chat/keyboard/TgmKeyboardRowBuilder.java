package org.frikadelki.ash.telegram.api.chat.keyboard;

import lombok.NonNull;
import org.frikadelki.ash.telegram.api.keyboard.TgmKeyboardButton;
import java.util.List;


public interface TgmKeyboardRowBuilder<TSelf extends TgmKeyboardRowBuilder> {

	TSelf button(@NonNull String title);

	TSelf button(@NonNull TgmKeyboardButton button);

	List<TgmKeyboardButton> build();
}
