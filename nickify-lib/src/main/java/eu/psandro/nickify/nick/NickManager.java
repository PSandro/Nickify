package eu.psandro.nickify.nick;

import eu.psandro.nickify.team.Nickable;
import eu.psandro.nickify.team.TeamViewLayout;
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
