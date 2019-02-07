package eu.psandro.nickify.nick.packet;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.google.common.base.Preconditions;
import eu.psandro.nickify.nick.NickProfile;
import lombok.NonNull;
import org.bukkit.plugin.Plugin;

import java.util.Set;


public final class PacketHandler {

    private final ProtocolManager protocolManager;
    private final Plugin plugin;
    private final @NonNull
    Set<NickProfile> nicks;

    public PacketHandler(final Plugin plugin, final @NonNull Set<NickProfile> nicks) {
        Preconditions.checkNotNull(plugin, "Plugin cannot be null!");
        this.plugin = plugin;
        this.protocolManager = ProtocolLibrary.getProtocolManager();
        this.nicks = nicks;
    }

    public void registerListener() {
        this.protocolManager.addPacketListener(new PlayerInfoAdapter(this.plugin, this.nicks));
        this.protocolManager.addPacketListener(new TabCompleteAdapter(this.plugin, this.nicks));
    }
}
