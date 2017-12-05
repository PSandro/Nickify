package de.psandro.nickify.controller.team;

import lombok.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
@EqualsAndHashCode
public final class ObserverEntity {
    private final @NonNull
    UUID uuid;
    private final @NonNull
    TeamView teamView;
    @Setter
    private boolean ignoreNameChange;

    public ObserverEntity(UUID uuid, TeamView teamView, boolean ignoreNameChange) {
        this.uuid = uuid;
        this.teamView = new TeamView(teamView);
        this.ignoreNameChange = ignoreNameChange;
    }

    public void changeName(String name) {
        this.changeName(name, false);
    }

    public void changeName(String name, boolean force) {
        if (this.ignoreNameChange && !force) return;
        final Player player = Bukkit.getPlayer(this.uuid);
        if (player == null) return;
        this.teamView.buildMemberRemovePacket().sendPacket(player);
        this.teamView.setOwner(name);
        this.teamView.buildMemberAddPacket().sendPacket(player);
    }

    public void updateView() {
        final Player player = Bukkit.getPlayer(this.uuid);
        if (player == null) return;
        this.teamView.buildUpdatePacket().sendPacket(player);
    }

    public void spawnView() {
        final Player player = Bukkit.getPlayer(this.uuid);
        if (player == null) return;
        this.teamView.buildCreationPacket().sendPacket(player);
    }

    public void despawnView() {
        final Player player = Bukkit.getPlayer(this.uuid);
        if (player == null) return;
        this.teamView.buildRemovePacket().sendPacket(player);
    }

    public void updateTeamView(TeamViewLayout layout) {
        layout.executeOn(this.teamView);
    }

}
