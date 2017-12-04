package de.psandro.nickify.controller.team;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.UUID;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public final class ObserverEntity {
    private final @NonNull UUID uuid;
    private final @Nullable TeamView teamView;

    public void nameChange(String name) {
        final Player player = Bukkit.getPlayer(this.uuid);
        if (player == null) return;
        this.teamView.buildMemberRemovePacket().sendPacket(player);
        this.teamView.setOwner(name);
        this.teamView.buildMemberAddPacket().sendPacket(player);
    }
}
