Telegram Bot API
====================

Simple telegram bot api implementation for Java.
Contains:
- bot api model classes *(in progress)*
- some generic parsing & response handling code *(mostly done)*
- simple update dispatcher *(in progress)*

The api is io-agnostic so you'll need to provide your own IO interface.
- org.frikadelki.ash.telegram.runtime.TgmQueryIO

Key entry classes:
- org.frikadelki.ash.telegram.api.TgmBotApi
- org.frikadelki.ash.telegram.bot.TgmBot
- org.frikadelki.ash.telegram.runtime.TgmBotRuntime