package eu.psandro.nickify.team;

import eu.psandro.nickify.nick.PlayerData;

import java.util.Set;
import java.util.UUID;

@FunctionalInterface
public interface TeamNickUpdateConsumer {

    void accept(PlayerData player, Nickable nickable, Set<UUID> exceptions);
}
