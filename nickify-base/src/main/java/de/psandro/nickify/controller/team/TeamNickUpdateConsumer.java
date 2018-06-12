package de.psandro.nickify.controller.team;

import de.psandro.nickify.api.nick.NickifyPlayerData;

import java.util.Set;
import java.util.UUID;

@FunctionalInterface
public interface TeamNickUpdateConsumer {

    void accept(NickifyPlayerData player, Nickable nickable, Set<UUID> exceptions);
}
