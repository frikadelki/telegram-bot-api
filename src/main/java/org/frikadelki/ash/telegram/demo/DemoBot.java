/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/10/16
 */

package org.frikadelki.ash.telegram.demo;

import lombok.NonNull;
import lombok.extern.java.Log;
import lombok.val;
import org.frikadelki.ash.telegram.api.TgmBotApiUpdates;
import org.frikadelki.ash.telegram.api.base.TgmUpdate;
import org.frikadelki.ash.telegram.api.chat.TgmBotApiChat;
import org.frikadelki.ash.telegram.api.chat.keyboard.TgmKeyboardButton;
import org.frikadelki.ash.telegram.api.chat.keyboard.TgmReplyKeyboardMarkup;
import org.frikadelki.ash.telegram.api.message.TgmMessageEntityBotCommand;
import org.frikadelki.ash.telegram.bot.TgmBot;
import org.frikadelki.ash.telegram.bot.commands.TgmCommandBody;
import org.frikadelki.ash.telegram.bot.filters.TgmNewMessageFilters;
import org.frikadelki.ash.telegram.runtime.TgmQueryIODefault;
import org.frikadelki.ash.telegram.runtime.dispatch.TgmUpdateDispatchContext;
import org.frikadelki.ash.telegram.runtime.dispatch.TgmUpdateDispatchContextNull;
import org.frikadelki.ash.toolset.utils.Lambda;

import java.util.Collections;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


@Log
public final class DemoBot {
	public static class Main {
		public static void main(final String[] args) {
			val botToken = "YOUR BOT TOKEN";
			val demoBot = new DemoBot(botToken);
			demoBot.enableConsoleLogging();
			demoBot.run();

			// access it via
			// https://telegram.me/<YOUR BOT>
		}
	}

	private final TgmBot tgmBot;

	public DemoBot(@NonNull final String botToken) {
		tgmBot = new TgmBot(botToken, new TgmQueryIODefault());
		setupBasicCommands();
	}

	public void enableConsoleLogging() {
		val consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(Level.ALL);
		consoleHandler.setFormatter(new SimpleFormatter());

		val logger = Logger.getLogger(getClass().getPackage().getName());
		logger.setLevel(Level.ALL);
		logger.addHandler(consoleHandler);
	}

	private void setupBasicCommands() {
		tgmBot.addNewMessageCommandHandler("/start", TgmNewMessageFilters.HAS_SENDER, new TgmCommandBody() {
			@Override
			public void dispatchCommand(final TgmUpdateDispatchContext context, final TgmUpdate update, final TgmMessageEntityBotCommand command) {
				val args = command.getArguments();
				if (0 != args.length && "secret".equalsIgnoreCase(args[0].getValue())) {
					// deeply linked user
					greetSecret(update);
				} else {
					// a total stranger
					greetStranger(update);
				}
			}
		});
	}

	private void sayWelcome(final long chatId, final String text) {
		tgmBot.getApi().getChatApi().sendMessage(TgmBotApiChat.SendMessageParams
				.builder()
				.chatId(chatId)
				.text(text)
				.build());
	}

	public void run() {
		// One-time setup to initialize some internal structures that
		// require network interactions with Telegram API.
		LOG.fine("[warmup-started]");
		tgmBot.warmup();
		LOG.fine("[warmup-completed]");

		long offset = -1L;
		val timeout = 10L;
		while (true) {
			LOG.fine("[updates-start-poll]");
			val pollParams = new TgmBotApiUpdates.UpdatesParams(offset + 1, null, timeout);
			val updatesResult = tgmBot.getApi().getUpdatesApi().getUpdates(pollParams);
			if (updatesResult.isSuccess()) {
				for (val update : updatesResult.getData()) {
					LOG.fine("[update-message]: " + update.getNewMessage().getText());
					offset = Math.max(update.getUpdateId(), offset);
					tgmBot.dispatchUpdate(new TgmUpdateDispatchContextNull(), update);
				}
			} else {
				LOG.severe("[updates-failed]: " + updatesResult.getError().toString());
			}
		}
	}

	private void greetStranger(final TgmUpdate update) {
		sendMessage(update.getNewMessage().getChat().getId(), "Hello stranger. Do you know the secret?", null);
	}

	private void greetSecret(final TgmUpdate update) {
		sendMessage(update.getNewMessage().getChat().getId(), "Welcome friend ...", new Lambda.Code1<TgmBotApiChat.SendMessageParams.Builder>() {
			@Override
			public void invoke(final TgmBotApiChat.SendMessageParams.Builder params) {
				val startAgainButton = new TgmKeyboardButton("Repeat magic words -> [ /start secret ]");
				val requestContactButton = new TgmKeyboardButton("Show them your credentials", true, null);
				val requestLocationButton = new TgmKeyboardButton("Materialize from thin air", null, true);

				params.replyMarkup(TgmReplyKeyboardMarkup
						.builder()
						.row(Collections.singletonList(startAgainButton))
						.row(Collections.singletonList(requestContactButton))
						.row(Collections.singletonList(requestLocationButton))
						.resizeKeyboard(true)
						.build());
			}
		});
	}

	private void sendMessage(final long chatId, @NonNull final String text, final Lambda.Code1<TgmBotApiChat.SendMessageParams.Builder> messageBuilder) {
		sendMessage(new Lambda.Code1<TgmBotApiChat.SendMessageParams.Builder>() {
			@Override
			public void invoke(final TgmBotApiChat.SendMessageParams.Builder params) {
				params.chatId(chatId).text(text);
				Lambda.safeCall(messageBuilder, params);
			}
		});
	}

	private void sendMessage(@NonNull final Lambda.Code1<TgmBotApiChat.SendMessageParams.Builder> messageBuilder) {
		val paramsBuilder = TgmBotApiChat.SendMessageParams.builder();
		messageBuilder.invoke(paramsBuilder);
		tgmBot.getApi().getChatApi().sendMessage(paramsBuilder.build());
	}
}
