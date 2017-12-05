package de.psandro.nickify.controller.team;

import de.psandro.nickify.controller.nick.Nickable;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public interface TeamNickUpdateConsumer {

    void accept(Player player, Nickable nickable, Set<UUID> exceptions);
}
