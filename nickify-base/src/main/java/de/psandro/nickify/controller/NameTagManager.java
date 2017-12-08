package de.psandro.nickify.controller;

import de.psandro.nickify.controller.nick.NickManager;
import de.psandro.nickify.controller.team.TeamManager;

public interface NameTagManager {

    TeamManager getTeamManager();

    NickManager getNickManager();

}
