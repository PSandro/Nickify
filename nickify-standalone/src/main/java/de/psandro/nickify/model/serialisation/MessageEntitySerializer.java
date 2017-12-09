package de.psandro.nickify.model.serialisation;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import de.psandro.nickify.controller.message.MessageFormat;
import de.psandro.nickify.model.MessageEntity;

import java.lang.reflect.Type;
import java.util.Set;

public final class MessageEntitySerializer implements JsonSerializer<MessageEntity>, JsonDeserializer<MessageEntity> {

    private static final Type MESSAGES_TYPE = new TypeToken<Set<MessageFormat>>() {
    }.getType();

    @Override
    public MessageEntity deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject json = jsonElement.getAsJsonObject();
        if (!json.has("messages")) throw new JsonParseException("Missing messages!");
        final Set<MessageFormat> messages = context.deserialize(json.get("messages"), MESSAGES_TYPE);
        return new MessageEntity(messages);
    }

    @Override
    public JsonElement serialize(MessageEntity src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject object = new JsonObject();
        object.add("messages", context.serialize(src.getMessages(), MESSAGES_TYPE));

        return object;
    }
}
