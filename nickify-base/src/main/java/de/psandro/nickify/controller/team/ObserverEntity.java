package de.psandro.nickify.controller.team;

import de.psandro.nickify.api.team.AbstractObserver;
import de.psandro.nickify.api.team.AssignedTeamViewLayout;
import de.psandro.nickify.api.team.TeamViewLayout;
import lombok.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
@EqualsAndHashCode
public final class ObserverEntity extends AbstractObserver {

    public ObserverEntity(UUID uuid, AssignedTeamViewLayout teamView, boolean ignoreNameChange) {
        super(uuid, teamView, ignoreNameChange);
    }

    public ObserverEntity(UUID uuid, AssignedTeamViewLayout teamView) {
        super(uuid, teamView, false);
    }

    @Override
    public void changeName(AssignedTeamViewLayout teamView, boolean force) {
        if (super.isIgnoreNameChange() && !force) return;
        final Player player = Bukkit.getPlayer(super.getUuid());
        if (player == null) return;
        System.out.println("Namechange: from " + this.getTeamView().getOwner() + " to " + teamView.getOwner());
        this.despawnView();
        super.setTeamView(teamView);
        this.spawnView();
    }

    public void updateView() {
        final Player player = Bukkit.getPlayer(this.getUuid());
        if (player == null) return;
        TeamViewPacketBuilder.buildUpdatePacket(super.getTeamView()).sendPacket(player);
    }

    public void spawnView() {
        final Player player = Bukkit.getPlayer(this.getUuid());
        if (player == null) return;
        TeamViewPacketBuilder.buildCreationPacket(super.getTeamView()).sendPacket(player);
    }

    public void despawnView() {
        final Player player = Bukkit.getPlayer(this.getUuid());
        if (player == null) return;
        TeamViewPacketBuilder.buildRemovePacket(super.getTeamView()).sendPacket(player);
    }

    public void updateTeamView(TeamViewLayout layout) {
        layout.executeOn(this.getTeamView());
    }

}

