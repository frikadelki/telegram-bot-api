/*
 * ASH Toolset
 * Copyright 2016 ASH Dev Team
 * Created by d.morozov on 2016/8/21
 */

package org.frikadelki.ash.telegram.api.keyboard;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import java.lang.reflect.Type;
import java.util.List;

@Builder
public class KeyboardRow
{
    @Singular
    @NonNull private List<KeyboardButton> buttons;

    public static class KeyboardRowSerializer implements JsonSerializer<KeyboardRow> {
        public JsonElement serialize(KeyboardRow src, Type typeOfSrc, JsonSerializationContext context) {
            return context.serialize(src.buttons);
        };
    }
}
