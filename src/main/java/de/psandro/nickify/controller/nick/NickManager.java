package de.psandro.nickify.controller.nick;

import de.psandro.nickify.controller.team.TeamViewLayout;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public interface NickManager {

    @Nullable
    Nickable getNickable(UUID uuid);

    Collection<Nickable> getNickables();

    Nickable nick(Player player, UUID uuid, TeamViewLayout layout) throws ExecutionException, InterruptedException;

    Nickable nick(Player player, UUID uuid, TeamViewLayout layout, Set<UUID> exceptions) throws ExecutionException, InterruptedException;

    void unnick(Player player);

    void unnick(Player player, Set<UUID> exceptions);
}
