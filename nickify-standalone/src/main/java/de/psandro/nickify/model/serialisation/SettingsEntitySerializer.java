package de.psandro.nickify.model.serialisation;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import de.psandro.nickify.model.SettingsEntity;
import de.psandro.nickify.model.TeamViewPreset;

import java.lang.reflect.Type;
import java.util.Set;

public class SettingsEntitySerializer implements JsonSerializer<SettingsEntity>, JsonDeserializer<SettingsEntity> {

    private static final Type PRESETS_TYPE = new TypeToken<Set<TeamViewPreset>>() {
    }.getType();
    private static final Type NICK_PRESETS_TYPE = new TypeToken<Set<String>>() {
    }.getType();

    @Override
    public SettingsEntity deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject json = jsonElement.getAsJsonObject();
        if (!json.has("teamViewPresets") || !json.has("nickViewPresets"))
            throw new JsonParseException("Missing fields!");
        final Set<TeamViewPreset> teamViewPresets = context.deserialize(json.get("teamViewPresets"), PRESETS_TYPE);
        final Set<String> nickViewPresets = context.deserialize(json.get("nickViewPresets"), NICK_PRESETS_TYPE);
        return new SettingsEntity(teamViewPresets, nickViewPresets);
    }

    @Override
    public JsonElement serialize(SettingsEntity src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject object = new JsonObject();
        object.add("teamViewPresets", context.serialize(src.getTeamViewPresets(), PRESETS_TYPE));
        object.add("nickViewPresets", context.serialize(src.getNickTeamViewPresets(), NICK_PRESETS_TYPE));
        return object;
    }
}
