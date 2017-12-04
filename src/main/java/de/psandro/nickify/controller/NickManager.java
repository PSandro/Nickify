package de.psandro.nickify.controller;

import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public interface NickManager {

    @Nullable
    Nickable getNickable(UUID uuid);

    Collection<Nickable> getNickables();

    Nickable nick(Player player, String name) throws ExecutionException, InterruptedException;

    Nickable nick(Player player, UUID uuid) throws ExecutionException, InterruptedException;

    void unnick(Player player);
}
