package de.psandro.nickify.controller.team;

import de.psandro.nickify.controller.nick.Nickable;
import org.bukkit.entity.Player;

public interface TeamNickUpdateConsumer {

    void accept(Player player, Nickable nickable);
}
