package eu.psandro.nickify;

import eu.psandro.nickify.nick.NickifyPlayerData;
import org.bukkit.entity.Player;

public interface NickifySpigotPlayer extends NickifyPlayerData {

    Player toSpigotPlayer();

}
