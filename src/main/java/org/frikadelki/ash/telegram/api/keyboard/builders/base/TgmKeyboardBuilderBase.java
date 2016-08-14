/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by dmoroz0v on 2016/8/28
 */

package org.frikadelki.ash.telegram.api.keyboard.builders.base;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.frikadelki.ash.telegram.api.keyboard.TgmKeyboardButton;
import org.frikadelki.ash.toolset.utils.StreamCompat;

import java.util.ArrayList;
import java.util.List;


public abstract class TgmKeyboardBuilderBase<T extends TgmKeyboardBuilderBase> {

	@NonNull protected final ArrayList<List<TgmKeyboardButton>> keyboard = new ArrayList<>();

	public T row(@NonNull final List<TgmKeyboardButton> row) {
		keyboard.add(StreamCompat.toList(row));
		return getSelf();
	}

	public KeyboardRowBuilder row() {
		return new KeyboardRowBuilder(getSelf());
	}

	protected abstract T getSelf();

	@RequiredArgsConstructor
	public final class KeyboardRowBuilder extends TgmKeyboardRowBuilderBase<KeyboardRowBuilder> {

		@NonNull private final T keyboardBuilder;

		public T endRow() {
			keyboardBuilder.row(keyboardRow);
			return keyboardBuilder;
		}

		@Override
		protected KeyboardRowBuilder getSelf() {
			return this;
		}
	}
}
