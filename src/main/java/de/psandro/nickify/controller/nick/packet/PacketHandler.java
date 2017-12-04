package de.psandro.nickify.controller.nick.packet;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.google.common.base.Preconditions;
import de.psandro.nickify.controller.nick.NickManager;
import org.bukkit.plugin.Plugin;


public final class PacketHandler {

    private final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
    private final NickManager nickManager;
    private final Plugin plugin;

    public PacketHandler(final NickManager nickManager, final Plugin plugin) {
        Preconditions.checkNotNull(nickManager, "NickManager cannot be null!");
        Preconditions.checkNotNull(plugin, "Plugin cannot be null!");
        this.nickManager = nickManager;
        this.plugin = plugin;
    }

    public void registerListener() {
        this.protocolManager.addPacketListener(new PlayerInfoAdapter(this.plugin, this.nickManager));
        this.protocolManager.addPacketListener(new TabCompleteAdapter(this.plugin, this.nickManager));
    }
}
