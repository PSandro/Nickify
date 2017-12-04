package de.psandro.nickify.model;

import com.google.gson.*;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.UUID;

public final class ProfileDeserializer implements JsonDeserializer<GameProfile> {
    @Override
    public GameProfile deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        UUID id = object.has("id") ? (UUID) context.deserialize(object.get("id"), UUID.class) : null;
        String name = object.has("name") ? object.getAsJsonPrimitive("name").getAsString() : null;
        GameProfile profile = new GameProfile(id, name);

        if (object.has("properties")) {
            for (Map.Entry<String, Property> prop : ((PropertyMap) context.deserialize(object.get("properties"),
                    PropertyMap.class)).entries()) {
                profile.getProperties().put(prop.getKey(), prop.getValue());
            }
        }
        return profile;
    }
}
