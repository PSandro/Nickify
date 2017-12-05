package de.psandro.nickify.controller.team;

import lombok.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.UUID;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public final class ObserverEntity {
    private final @NonNull
    UUID uuid;
    private final @Nullable
    TeamView teamView;
    @Setter
    private boolean ignoreNameChange;

    public void changeName(String name) {
        this.changeName(name, false);
    }

    public void changeName(String name, boolean force) {
        if (!this.ignoreNameChange && !force) return;
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

}
