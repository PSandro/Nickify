package eu.psandro.nickify;

import eu.psandro.nickify.nick.NickManager;
import eu.psandro.nickify.team.TeamManager;

public interface NameTagManager {

    TeamManager getTeamManager();

    NickManager getNickManager();

}
