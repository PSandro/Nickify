package de.psandro.nickify.model;


import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.psandro.nickify.controller.message.MessageFormat;
import de.psandro.nickify.controller.message.MessageId;
import de.psandro.nickify.controller.team.TeamViewLayout;
import de.psandro.nickify.model.serialisation.MessageEntitySerializer;
import de.psandro.nickify.model.serialisation.TeamViewPresetSerializer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Map;
import java.util.Set;

public final class FileManager {

    private static final File FOLDER = new File("plugins" + File.separator + "Nickify");
    private static final File CONFIG_FILE = new File(FOLDER, "settings.json");
    private static final File MESSAGE_FILE = new File(FOLDER, "messages.json");
    private static final Charset CHARSET = Charsets.UTF_8;

    private static final MessageEntity DEFAULT_MESSAGE_ENTITY;
    private static final SettingsEntity DEFAULT_SETTINGS_ENTITY;


    static {
        final Map<MessageId, MessageFormat> messageFormats = Maps.newHashMap();
        messageFormats.put(MessageId.NICK, new MessageFormat("&6You received following nickname: %nickname%", MessageId.NICK, true));
        messageFormats.put(MessageId.UNKNOWN_ERROR, new MessageFormat("&cAn unknown error occured: %error%", MessageId.UNKNOWN_ERROR, true));
        messageFormats.put(MessageId.NO_PERMISSION, new MessageFormat("&cYou don't have permission to perform this command: %command%", MessageId.NO_PERMISSION, true));


        DEFAULT_MESSAGE_ENTITY = new MessageEntity(messageFormats);

        final Set<TeamViewPreset> teamViewPresets = Sets.newHashSet(
                new TeamViewPreset("default", new TeamViewLayout("§7Player ", " §6ok", 10), "nametag.player"),
                new TeamViewPreset("premium", new TeamViewLayout("§7Prem ", " §6yep", 1), "nametag.premium"),
                new TeamViewPreset("admin", new TeamViewLayout("§cAdmin ", "", 0), "nametag.admin")
        );

        final Set<String> nickTeamViewPresets = Sets.newHashSet(
                "default", "premium"
        );

        DEFAULT_SETTINGS_ENTITY = new SettingsEntity(teamViewPresets, nickTeamViewPresets);
    }

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(TeamViewLayout.class, new TeamViewLayoutSerializer())
            .registerTypeAdapter(TeamViewPreset.class, new TeamViewPresetSerializer())
            .registerTypeAdapter(MessageEntity.class, new MessageEntitySerializer())
            .setPrettyPrinting()
            .create();

    public FileManager() throws IOException {
        this.createFiles();
    }

    private void createFiles() throws IOException {
        if (!FOLDER.exists()) FOLDER.mkdirs();
        if (!CONFIG_FILE.exists()) {
            CONFIG_FILE.createNewFile();
            this.writeSettings(DEFAULT_SETTINGS_ENTITY);
        }
        if (!MESSAGE_FILE.exists()) {
            MESSAGE_FILE.createNewFile();
            this.writeMessages(DEFAULT_MESSAGE_ENTITY);
        }
    }

    public void writeMessages(final MessageEntity entity) throws IOException {
        final String data = GSON.toJson(entity);
        this.write(MESSAGE_FILE, data);
    }

    public void writeSettings(final SettingsEntity entity) throws IOException {
        final String data = GSON.toJson(entity);
        this.write(CONFIG_FILE, data);
    }

    public MessageEntity readMessages() throws IOException {
        return GSON.fromJson(this.read(MESSAGE_FILE), MessageEntity.class);
    }

    public SettingsEntity readSettings() throws IOException {
        return GSON.fromJson(this.read(CONFIG_FILE), SettingsEntity.class);
    }

    private String read(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath()), CHARSET);
    }

    private void write(File file, String data) throws IOException {
        Files.write(file.toPath(), data.getBytes(CHARSET));
    }


}
