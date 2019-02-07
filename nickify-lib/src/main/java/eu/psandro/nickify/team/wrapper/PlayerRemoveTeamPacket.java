package eu.psandro.nickify.team.wrapper;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class PlayerRemoveTeamPacket extends TeamPacket {

    public PlayerRemoveTeamPacket(String name) {
        super(name, 4);
    }

    public PlayerRemoveTeamPacket(String name, String ownerName) {
        super(name, 4);
        this.setPlayers(Collections.singletonList(ownerName));
    }

    public List<String> getPlayers() {
        return (List<String>) this.handle.getSpecificModifier(Collection.class).read(0);
    }

    public void setPlayers(List<String> value) {
        this.handle.getSpecificModifier(Collection.class).write(0, value);
    }
}
