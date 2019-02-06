package eu.psandro.nickify.team.wrapper;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;

public abstract class TeamPacket extends AbstractPacket {

    private static final PacketType TYPE = PacketType.Play.Server.SCOREBOARD_TEAM;


    public TeamPacket(final String name, int mode) {
        super(new PacketContainer(TYPE), TYPE);
        this.handle.getModifier().writeDefaults();
        this.setName(name);
        this.setMode(mode);
    }

    public String getName() {
        return (String) this.handle.getStrings().read(0);
    }

    private void setName(String value) {
        this.handle.getStrings().write(0, value);
    }

    public int getMode() {
        return ((Integer) this.handle.getIntegers().read(1)).intValue();
    }

    private void setMode(int value) {
        this.handle.getIntegers().write(1, value);
    }

}
