/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by dmoroz0v on 2016/8/23
 */

package org.frikadelki.ash.telegram.api.keyboard.builders;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.frikadelki.ash.telegram.api.keyboard.TgmKeyboardButton;
import org.frikadelki.ash.telegram.api.keyboard.TgmReplyKeyboardMarkup;
import org.frikadelki.ash.telegram.api.keyboard.builders.base.TgmKeyboardBuilderBase;
import org.frikadelki.ash.toolset.utils.Lambda;
import org.frikadelki.ash.toolset.utils.StreamCompat;

import java.util.ArrayList;
import java.util.List;


public final class TgmReplyKeyboardMarkupBuilderOld {

	@NonNull private List<List<TgmKeyboardButton>> keyboard = new ArrayList<>();
	private Boolean resizeKeyboard = null;
	private Boolean oneTimeKeyboard = null;
	private Boolean selective = null;

	public static TgmReplyKeyboardMarkupBuilderOld make() {
		return new TgmReplyKeyboardMarkupBuilderOld();
	}

	public TgmReplyKeyboardMarkupBuilderOld keyboard(@NonNull final List<List<TgmKeyboardButton>> keyboard) {
		this.keyboard = StreamCompat.toList(StreamCompat.select(keyboard, new Lambda.FactoryCode1<List<TgmKeyboardButton>, List<TgmKeyboardButton>>() {
			@Override
			public List<TgmKeyboardButton> produce(final List<TgmKeyboardButton> unknown) {
				return new ArrayList<>(unknown);
			}
		}));
		return this;
	}

	public KeyboardBuilder keyboard() {
		return new KeyboardBuilder(this);
	}

	public TgmReplyKeyboardMarkupBuilderOld resizeKeyboard(final Boolean resizeKeyboard) {
		this.resizeKeyboard = resizeKeyboard;
		return this;
	}

	public TgmReplyKeyboardMarkupBuilderOld oneTimeKeyboard(final Boolean oneTimeKeyboard) {
		this.oneTimeKeyboard = oneTimeKeyboard;
		return this;
	}

	public TgmReplyKeyboardMarkupBuilderOld selective(final Boolean selective) {
		this.selective = selective;
		return this;
	}

	public TgmReplyKeyboardMarkup build() {
		return new TgmReplyKeyboardMarkup(keyboard, resizeKeyboard, oneTimeKeyboard, selective);
	}

	@RequiredArgsConstructor
	public final class KeyboardBuilder extends TgmKeyboardBuilderBase<KeyboardBuilder> {

		@NonNull private final TgmReplyKeyboardMarkupBuilderOld replyKeyboardMarkupBuilder;

		public TgmReplyKeyboardMarkupBuilderOld endKeyboard() {
			return replyKeyboardMarkupBuilder.keyboard(keyboard);
		}

		@Override
		protected KeyboardBuilder getSelf() {
			return this;
		}
	}
}
