package eu.psandro.nickify;

import com.google.gson.*;
import eu.psandro.nickify.team.TeamViewLayout;

import java.lang.reflect.Type;

public final class TeamViewLayoutSerializer implements JsonSerializer<TeamViewLayout>, JsonDeserializer<TeamViewLayout> {

    @Override
    public TeamViewLayout deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();

        if (!jsonObject.has("prefix") || !jsonObject.has("suffix") || !jsonObject.has("priority"))
            throw new JsonParseException("Fields missing");


        return new TeamViewLayout(
                jsonObject.get("prefix").getAsString(),
                jsonObject.get("suffix").getAsString(),
                jsonObject.get("priority").getAsInt());
    }

    @Override
    public JsonElement serialize(TeamViewLayout layout, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("prefix", layout.getPrefix());
        jsonObject.addProperty("suffix", layout.getSuffix());
        jsonObject.addProperty("priority", layout.getPriority());
        return jsonObject;
    }
}
