package de.psandro.nickify.controller.nick;

import de.psandro.nickify.api.nick.NickEntity;
import de.psandro.nickify.api.team.TeamViewLayout;
import de.psandro.nickify.controller.team.Nickable;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public interface NickManager {

    Nickable getNickable(UUID uuid);

    NickEntity getNickEntity(String nickname);

    NickEntity getNickEntity(UUID nickUuid);

    Collection<Nickable> getNickables();

    Nickable nick(Player player, UUID uuid, TeamViewLayout layout) throws ExecutionException, InterruptedException;

    Nickable nick(Player player, UUID uuid, TeamViewLayout layout, Set<UUID> exceptions) throws ExecutionException, InterruptedException;

    void unnick(Player player);

    void unnick(Player player, Set<UUID> exceptions);
}
