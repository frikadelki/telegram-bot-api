/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by ein on 2016/7/18
 */

package org.frikadelki.ash.toolset.utils;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.NonNull;
import lombok.Setter;

import java.io.IOException;
import java.util.HashMap;


public final class GsonEnumFallbackTypeAdapter<T extends Enum<T>> extends TypeAdapter<T> {
	private final HashMap<String, T> namesToValues = new HashMap<>();
	private final HashMap<T, String> valuesToNames = new HashMap<>();

	private final T fallbackValue;
	@Setter private boolean serializeFallbackValue = false;

	public GsonEnumFallbackTypeAdapter(@NonNull Class<T> enumClass, final T fallbackValue) {
		try {
			for (final T enumConstant : enumClass.getEnumConstants()) {
				initConstant(enumClass, enumConstant);
			}
		}
		catch (NoSuchFieldException e) {
			AshAssert.fail();
		}
		this.fallbackValue = fallbackValue;
	}

	private void initConstant(@NonNull Class<T> enumClass, @NonNull final T enumConstant) throws NoSuchFieldException {
		final String constantName = enumConstant.name();
		final SerializedName annotation = enumClass.getField(constantName).getAnnotation(SerializedName.class);
		final String serializedName;
		if (null != annotation) {
			serializedName = annotation.value();
		} else {
			serializedName = constantName;
		}
		namesToValues.put(serializedName, enumConstant);
		valuesToNames.put(enumConstant, serializedName);
	}

	@Override
	public void write(final JsonWriter out, final T value) throws IOException {
		if ((null == value) || ((value == fallbackValue) && !serializeFallbackValue)) {
			out.nullValue();
			return;
		}
		out.value(valuesToNames.get(value));
	}

	@Override
	public T read(final JsonReader in) throws IOException {
		final String name = in.nextString();
		final T value = namesToValues.get(name);
		if (null == value) {
			return fallbackValue;
		}
		return value;
	}
}
