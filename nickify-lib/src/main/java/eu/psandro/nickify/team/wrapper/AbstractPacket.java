package eu.psandro.nickify.team.wrapper;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public abstract class AbstractPacket {
    transient PacketContainer handle;

    AbstractPacket(PacketContainer handle) {
        if (handle == null) {
            throw new IllegalArgumentException("Packet handle cannot be null.");
        } else {
            this.handle = handle;
        }
    }

    private PacketContainer getHandle() {
        return this.handle;
    }

    public void sendPacket(Player receiver) {
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(receiver, this.getHandle());
        } catch (InvocationTargetException var3) {
            throw new RuntimeException("Cannot send packet.", var3);
        }
    }

    public void receivePacket(Player sender) {
        try {
            ProtocolLibrary.getProtocolManager().recieveClientPacket(sender, this.getHandle());
        } catch (Exception var3) {
            throw new RuntimeException("Cannot recieve packet.", var3);
        }
    }
}
