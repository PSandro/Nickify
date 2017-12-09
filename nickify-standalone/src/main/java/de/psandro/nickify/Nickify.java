package de.psandro.nickify;

import de.psandro.nickify.controller.DefaultNameTagManager;
import de.psandro.nickify.controller.NameTagManager;
import de.psandro.nickify.controller.listener.RegisterListener;
import de.psandro.nickify.controller.nick.DefaultNickManager;
import de.psandro.nickify.controller.nick.INickEntityFactory;
import de.psandro.nickify.controller.nick.NickManager;
import de.psandro.nickify.controller.nick.packet.PacketHandler;
import de.psandro.nickify.controller.team.DefaultTeamManager;
import de.psandro.nickify.controller.team.TeamManager;
import de.psandro.nickify.misc.TeamViewFetcher;
import de.psandro.nickify.model.*;
import de.psandro.nickify.view.command.NickCommand;
import de.psandro.nickify.view.command.UnnickCommand;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;

public final class Nickify extends JavaPlugin {

    @Getter
    private static NameTagManager nameTagManager;
    private ConfigManager configManager;


    private ConfigManager initConfigManager() {
        ConfigManager configManager;
        try {
            configManager = new ConfigManager();
            configManager.init();
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.WARNING, "Nickify could not load the configuration! Disabling...");
            e.printStackTrace();
            this.getPluginLoader().disablePlugin(this);
            return null;
        }

        final Optional<Throwable> validation = configManager.validate();

        if (validation.isPresent()) {
            Bukkit.getLogger().log(Level.WARNING, "The Nickify configuration is invalid: " + validation.get().getMessage());
            this.getPluginLoader().disablePlugin(this);
            return null;
        }
        return configManager;
    }


    @Override
    public void onEnable() {
        this.configManager = this.initConfigManager();
        if (this.configManager == null) return;

        final SettingsEntity settingsEntity = this.configManager.getSettingsEntity();
        final MessageEntity messageEntity = this.configManager.getMessageEntity();

        final TeamManager teamManager = new DefaultTeamManager();
        final INickEntityFactory entityFactory = new NickEntityFactory();
        final NickManager nickManager = new DefaultNickManager(entityFactory, this, teamManager.getUpdateConsumer());
        final TeamViewFetcher teamViewFetcher = new TeamViewFetcher(
                settingsEntity.getTeamViewPresets(),
                settingsEntity.getDefaultPreset(),
                settingsEntity.getNickPresets());
        final MessageManager messageManager = new MessageManager(messageEntity.getMessages());
        nameTagManager = new DefaultNameTagManager(teamManager, nickManager);
        final PacketHandler packetHandler = new PacketHandler(nameTagManager.getNickManager(), this);
        packetHandler.registerListener();

        this.getServer().getPluginManager().registerEvents(new RegisterListener(nameTagManager, teamViewFetcher), this);
        this.getCommand("nick").setExecutor(new NickCommand(nameTagManager, messageManager, teamViewFetcher));
        this.getCommand("unnick").setExecutor(new UnnickCommand(nameTagManager, messageManager));
    }

    @Override
    public void onDisable() {
        if (this.configManager != null) {
            try {
                this.configManager.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
