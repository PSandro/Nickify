package eu.psandro.nickify.team;

import eu.psandro.nickify.nick.NickifyPlayerData;

import java.util.Set;
import java.util.UUID;

@FunctionalInterface
public interface TeamNickUpdateConsumer {

    void accept(NickifyPlayerData player, Nickable nickable, Set<UUID> exceptions);
}
