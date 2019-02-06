package eu.psandro.nickify;

import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedSignedProperty;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.UUID;

public final class ProfileDeserializer implements JsonDeserializer<WrappedGameProfile> {
    @Override
    public WrappedGameProfile deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        UUID id = object.has("id") ? (UUID) context.deserialize(object.get("id"), UUID.class) : null;
        String name = object.has("name") ? object.getAsJsonPrimitive("name").getAsString() : null;
        WrappedGameProfile profile = new WrappedGameProfile(id, name);

        if (object.has("properties")) {
            final JsonArray properties = object.getAsJsonArray("properties");
            properties.forEach(property -> {
                final JsonObject jsonObject = property.getAsJsonObject();
                final String propertyName = jsonObject.get("name").getAsString();
                final String propertyValue = jsonObject.get("value").getAsString();
                final String propertySignature = jsonObject.get("signature").getAsString();

                profile.getProperties().put(propertyName, new WrappedSignedProperty(propertyName, propertyValue, propertySignature));
            });
        }
        return profile;
    }
}
