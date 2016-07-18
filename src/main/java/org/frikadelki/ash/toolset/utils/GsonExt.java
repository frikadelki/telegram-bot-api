// ASH Toolset
// Copyright 2016 ASH Dev Team
// Created by ein on 2016/5/20

package org.frikadelki.ash.toolset.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import lombok.NonNull;

import java.util.ArrayList;


public final class GsonExt {
	public static void forEach(@NonNull final JsonArray array, @NonNull final Lambda.Code1<JsonElement> action) {
		final int size = array.size();
		for (int i = 0; i < size; i++) {
			final JsonElement element = array.get(i);
			action.invoke(element);
		}
	}

	public static <W> ArrayList<W> selectNonNull(@NonNull final JsonArray array, @NonNull final Lambda.FactoryCode1<W, JsonElement> select) {
		final ArrayList<W> result = new ArrayList<>(array.size());
		forEach(array, new Lambda.Code1<JsonElement>() {
			@Override
			public void invoke(JsonElement element) {
				final W selected = select.produce(element);
				if (null != selected) {
					result.add(selected);
				}
			}
		});
		return result;
	}
}
