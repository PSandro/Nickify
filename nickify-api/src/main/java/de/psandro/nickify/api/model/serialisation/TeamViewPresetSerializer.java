package de.psandro.nickify.api.model.serialisation;

import com.google.gson.*;
import de.psandro.nickify.api.model.TeamViewPreset;
import de.psandro.nickify.api.team.TeamViewLayout;

import java.lang.reflect.Type;

public final class TeamViewPresetSerializer implements JsonDeserializer<TeamViewPreset>, JsonSerializer<TeamViewPreset> {

    @Override
    public TeamViewPreset deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();

        if (!jsonObject.has("name") || !jsonObject.has("permission") || !jsonObject.has("layout"))
            throw new JsonParseException("Fields missing");

        final String name = jsonObject.get("name").getAsString();
        final String permission = jsonObject.get("permission").getAsString();
        final TeamViewLayout layout = context.deserialize(jsonObject.get("layout"), TeamViewLayout.class);
        return new TeamViewPreset(name, layout, permission);
    }

    @Override
    public JsonElement serialize(TeamViewPreset src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject json = new JsonObject();
        json.addProperty("name", src.getName());
        json.addProperty("permission", src.getPermission());
        json.add("layout", context.serialize(src.getLayout()));
        return json;
    }
}
