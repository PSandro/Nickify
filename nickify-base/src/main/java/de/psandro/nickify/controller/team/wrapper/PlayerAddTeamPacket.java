package de.psandro.nickify.controller.team.wrapper;

import java.util.Collection;
import java.util.List;

public final class PlayerAddTeamPacket extends TeamPacket {

    public PlayerAddTeamPacket(String name) {
        super(name, 3);
    }

    public List<String> getPlayers() {
        return (List) this.handle.getSpecificModifier(Collection.class).read(0);
    }

    public void setPlayers(List<String> value) {
        this.handle.getSpecificModifier(Collection.class).write(0, value);
    }

}
