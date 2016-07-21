/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/19
 */

package org.frikadelki.ash.telegram.runtime;

import com.google.gson.*;
import lombok.NonNull;
import org.frikadelki.ash.telegram.api.chat.TgmChat;
import org.frikadelki.ash.telegram.api.message.TgmMessageEntity;
import org.frikadelki.ash.toolset.utils.GsonEnumFallbackTypeAdapter;


public final class TgmJsonIO {
	private static TgmJsonIO PRIVATE_INSTANCE = new TgmJsonIO();

	private final JsonParser jsonParser = new JsonParser();
	private final Gson gson;

	private TgmJsonIO() {
		gson = new Builder()
				.registerEnumWithFallback(TgmChat.Type.class, TgmChat.Type.UNKNOWN)
				.registerEnumWithFallback(TgmMessageEntity.Type.class, TgmMessageEntity.Type.UNKNOWN)
				.build();
	}

	static String toJson(final Object object) {
		if (null == object) {
			return null;
		}
		return PRIVATE_INSTANCE.gson.toJson(object);
	}

	static JsonElement parseJsonElement(final String responseBody) throws JsonParseException {
		if (null == responseBody) {
			return null;
		}
		return PRIVATE_INSTANCE.jsonParser.parse(responseBody);
	}

	static <TData> TData fromJson(final JsonElement element, @NonNull final Class<TData> resultClass) throws JsonParseException {
		if (null == element) {
			return null;
		}
		return PRIVATE_INSTANCE.gson.fromJson(element, resultClass);
	}

	private static class Builder {
		private GsonBuilder builder = new GsonBuilder();
		<T extends Enum<T>> Builder registerEnumWithFallback(@NonNull final Class<T> enumClass, final T fallbackValue) {
			builder.registerTypeAdapter(enumClass, new GsonEnumFallbackTypeAdapter<>(enumClass, fallbackValue));
			return this;
		}

		Gson build() {
			return builder.create();
		}
	}
}
