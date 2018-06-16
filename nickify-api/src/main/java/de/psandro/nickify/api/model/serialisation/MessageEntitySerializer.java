package de.psandro.nickify.api.model.serialisation;


import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import de.psandro.nickify.api.model.MessageEntity;
import de.psandro.nickify.api.model.MessageFormat;
import de.psandro.nickify.api.model.MessageId;

import java.lang.reflect.Type;
import java.util.Map;

public final class MessageEntitySerializer implements JsonSerializer<MessageEntity>, JsonDeserializer<MessageEntity> {

    private static final Type MESSAGES_TYPE = new TypeToken<Map<MessageId, MessageFormat>>() {
    }.getType();

    @Override
    public MessageEntity deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject json = jsonElement.getAsJsonObject();
        if (!json.has("messages")) throw new JsonParseException("Missing messages!");
        final Map<MessageId, MessageFormat> messages = context.deserialize(json.get("messages").getAsJsonObject(), MESSAGES_TYPE);
        return new MessageEntity(messages);
    }

    @Override
    public JsonElement serialize(MessageEntity src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject object = new JsonObject();
        object.add("messages", context.serialize(src.getMessages(), MESSAGES_TYPE));
        return object;
    }
}
