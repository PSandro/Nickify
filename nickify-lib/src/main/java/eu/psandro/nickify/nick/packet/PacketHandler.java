package eu.psandro.nickify.nick.packet;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.google.common.base.Preconditions;
import eu.psandro.nickify.nick.NickManager;
import org.bukkit.plugin.Plugin;


public final class PacketHandler {

    private final ProtocolManager protocolManager;
    private final NickManager nickManager;
    private final Plugin plugin;

    public PacketHandler(final NickManager nickManager, final Plugin plugin) {
        Preconditions.checkNotNull(nickManager, "NickManager cannot be null!");
        Preconditions.checkNotNull(plugin, "Plugin cannot be null!");
        this.nickManager = nickManager;
        this.plugin = plugin;
        this.protocolManager = ProtocolLibrary.getProtocolManager();
    }

    public void registerListener() {
        this.protocolManager.addPacketListener(new PlayerInfoAdapter(this.plugin, this.nickManager));
        this.protocolManager.addPacketListener(new TabCompleteAdapter(this.plugin, this.nickManager));
    }
}
