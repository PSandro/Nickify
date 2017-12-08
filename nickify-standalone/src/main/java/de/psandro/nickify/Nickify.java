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
import de.psandro.nickify.model.NickEntityFactory;
import de.psandro.nickify.view.command.NickCommand;
import de.psandro.nickify.view.command.UnnickCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Nickify extends JavaPlugin {

    private NameTagManager nameTagManager;
    private PacketHandler packetHandler;

    @Override
    public void onLoad() {
        final TeamManager teamManager = new DefaultTeamManager();
        final INickEntityFactory entityFactory = new NickEntityFactory();
        final NickManager nickManager = new DefaultNickManager(entityFactory, this, teamManager.getUpdateConsumer());


        this.nameTagManager = new DefaultNameTagManager(teamManager, nickManager);
        this.packetHandler = new PacketHandler(this.nameTagManager.getNickManager(), this);

    }

    @Override
    public void onEnable() {
        this.packetHandler.registerListener();
        this.getServer().getPluginManager().registerEvents(new RegisterListener(this.nameTagManager, this), this);
        this.getCommand("nick").setExecutor(new NickCommand(this.nameTagManager));
        this.getCommand("unnick").setExecutor(new UnnickCommand(this.nameTagManager));
    }
}
