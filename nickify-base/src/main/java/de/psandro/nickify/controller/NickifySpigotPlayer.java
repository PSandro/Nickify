package de.psandro.nickify.controller;

import de.psandro.nickify.api.nick.NickifyPlayerData;
import org.bukkit.entity.Player;

public interface NickifySpigotPlayer extends NickifyPlayerData {

    Player toSpigotPlayer();

}
