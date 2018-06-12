package de.psandro.nickify.model;

import de.psandro.nickify.exception.ConfigurationException;
import lombok.Getter;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public final class ConfigManager implements Closeable {

    private final FileManager fileManager = new FileManager();

    @Getter
    private SettingsEntity settingsEntity;
    @Getter
    private MessageEntity messageEntity;

    public ConfigManager() throws IOException {
    }

    public void init() throws IOException {
        this.settingsEntity = this.fileManager.readSettings();
        this.messageEntity = this.fileManager.readMessages();
    }

    public Optional<Throwable> validate() {
        if (this.settingsEntity.getTeamViewPresets().size() <= 0)
            return Optional.of(new ConfigurationException("No TeamView presets given"));

        final List<String> invalidNickPresets = new ArrayList<>();
        final Set<String> presetNames = this.settingsEntity.getTeamViewPresets().stream().map(TeamViewPreset::getName).collect(Collectors.toSet());
        this.settingsEntity.getNickTeamViewPresets().forEach(nickPreset -> {
            if (!presetNames.contains(nickPreset)) {
                invalidNickPresets.add(nickPreset);
            }
        });

        if (invalidNickPresets.size() > 0) {
            final String nickPresets = invalidNickPresets.stream().collect(Collectors.joining(", "));
            return Optional.of(new ConfigurationException("Following nickPreset names do not have a TeamViewPreset to reference to: " + nickPresets));
        }

        if (!presetNames.contains("default")) {
            return Optional.of(new ConfigurationException("A default TeamView preset with the name \"default\" was not found! You need to create one."));
        }


        return Optional.empty();
    }


    private void save() throws IOException {
        this.fileManager.writeMessages(this.messageEntity);
        this.fileManager.writeSettings(this.settingsEntity);
    }

    @Override
    public void close() throws IOException {
        this.save();
        this.settingsEntity = null;
        this.messageEntity = null;
    }
}
