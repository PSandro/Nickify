package de.psandro.nickify.controller.nick;

import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public interface NickManager {

    @Nullable
    Nickable getNickable(UUID uuid);

    Collection<Nickable> getNickables();

    Nickable nick(Player player, String name) throws ExecutionException, InterruptedException;

    Nickable nick(Player player, UUID uuid) throws ExecutionException, InterruptedException;

    void unnick(Player player);
}
