Telegram Bot API
====================

Simple telegram bot api implementation for Java.  
Contains:
- bot api `org.frikadelki.ash.telegram.api`
- - model classes: update, message, chat, keyboards, user, etc.
- - api signatures for most of the main methods: set webhook, fetch updates, send message, etc.
- - *(still some entities to implement to be done)*
- basic updates dispatch `org.frikadelki.ash.telegram.runtime.dispatch.TgmUpdateDispatchFront`
- filtered updates dispatch with built-in commands handling
- - `org.frikadelki.ash.telegram.bot.TgmBot#addGenericHandler`
- - `org.frikadelki.ash.telegram.bot.TgmBot#addNewMessageCommandHandler`
- - *(a bit too complex inside, needs simplification)*


### Short Manual ###

Full example `org.frikadelki.ash.telegram.demo.DemoBot`.

##### Basics #####

0. Check out [Telegram Bot API](https://core.telegram.org/bots/api) 
0. Register your bot with the [@BotFather](https://telegram.me/botfather)
0. In your main method, or some other safe place like that, create the bot instance
```
tgmBot = new TgmBot(botToken, new TgmQueryIODefault());
```

##### Handle Commands #####

You can use simple built-in commands dispatcher.
 
```
tgmBot.addNewMessageCommandHandler("/start", TgmNewMessageFilters.HAS_SENDER, new TgmCommandBody() {
			@Override
			public void dispatchCommand(final TgmUpdateDispatchContext context, final TgmUpdate update, final TgmMessageEntityBotCommand command) {
				val args = command.getArguments();

				if (0 != args.length && "secret".equalsIgnoreCase(args[0].getValue())) {
					// deeply linked user
					sayWelcome(update.getNewMessage().getChat().getId(), "Hello, it seems we have met somewhere before.");
				} else {
					// a total stranger
					sayWelcome(update.getNewMessage().getChat().getId(), "Hello stranger.");
				}
			}
		});
```

##### Send Messages #####
 
Most basic message requires text and chat id.
 
```
tgmBot.getApi().getChatApi().sendMessage(
				TgmBotApiChat.SendMessageParams
						.builder()
						.chatId(chatId)
						.text(text)
						.build());
```

Keyboard usage example.

```
val startAgainButton = new TgmKeyboardButton("Repeat magic words -> [ /start secret ]");
val requestContactButton = new TgmKeyboardButton("Show them your credentials", true, null);
val requestLocationButton = new TgmKeyboardButton("Materialize from thin air", null, true);

val keyboardMarkup = TgmReplyKeyboardMarkup
		.builder()
		.row(Collections.singletonList(startAgainButton))
		.row(Collections.singletonList(requestContactButton))
		.row(Collections.singletonList(requestLocationButton))
		.resizeKeyboard(true)
		.build());

tgmBot.getApi().getChatApi().sendMessage(TgmBotApiChat.SendMessageParams
		.builder()
		.chatId(chatId)
		.text(text)
		.replyMarkup(keyboardMarkup)
		.build());
```

##### Run Loop #####

In order for the whole thing to work you need to provide your own run loop 
that will fetch updates from telegram API and either consume them directly
or feed to build-in dispatcher that will invoke registered updates/commands
handlers.

Typical updates poll loop implementation can be found below.

```
// typicall updates poll loop
long offset = -1L;
val timeout = 10L;
while (true) {
	log.fine("[updates-start-poll]");
	val pollParams = new TgmBotApiUpdates.UpdatesParams(offset + 1, null, timeout);
	val updatesResult = tgmBot.getApi().getUpdatesApi().getUpdates(pollParams);

	if (updatesResult.isSuccess()) {
		for (val update : updatesResult.getData()) {
			log.fine("[update-message]: " + update.getNewMessage().getText());
			offset = Math.max(update.getUpdateId(), offset);
			tgmBot.dispatchUpdate(new TgmUpdateDispatchContextNull(), update);
		}
	} else {
		log.severe("[updates-failed]: " + updatesResult.getError().toString());
	}
}
```