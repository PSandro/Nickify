package eu.psandro.nickify.team.wrapper;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.google.common.base.Objects;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public abstract class AbstractPacket {
    PacketContainer handle;

    AbstractPacket(PacketContainer handle, PacketType type) {
        if (handle == null) {
            throw new IllegalArgumentException("Packet handle cannot be null.");
        } else if (!Objects.equal(handle.getType(), type)) {
            throw new IllegalArgumentException(handle.getHandle() + " is not a packet of type " + type);
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
