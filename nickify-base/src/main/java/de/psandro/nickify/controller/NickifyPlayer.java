package de.psandro.nickify.controller;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.bukkit.entity.Player;

import java.util.UUID;


@AllArgsConstructor
@EqualsAndHashCode
public final class NickifyPlayer implements NickifySpigotPlayer {

    private final @NonNull
    Player player;


    @Override
    public Player toSpigotPlayer() {
        return this.player;
    }

    @Override
    public String getRealName() {
        return this.player.getName();
    }

    @Override
    public UUID getUniqueId() {
        return this.player.getUniqueId();
    }


    public static NickifyPlayer fromBukkitPlayer(@NonNull final Player player) {
        return new NickifyPlayer(player);
    }

}
