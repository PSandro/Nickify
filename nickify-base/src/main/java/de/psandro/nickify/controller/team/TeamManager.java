package de.psandro.nickify.controller.team;

import de.psandro.nickify.api.team.AbstractObserver;
import de.psandro.nickify.api.team.TeamInfo;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.UUID;

public interface TeamManager {

    TeamController createTeamController(Player player, TeamView defaultTeamView, LinkedHashSet<AbstractObserver> observers, Nickable nickable);

    TeamController createTeamController(Player player, TeamView defaultTeamView, LinkedHashSet<AbstractObserver> observers);

    void spawnTeam(TeamController teamInfo);

    void deleteTeam(TeamInfo teamInfo);

    Collection<TeamInfo> getTeamInfos();

    void updateTeam(TeamInfo teamInfo);

    TeamController getTeamController(UUID uuid);

    TeamNickUpdateConsumer getUpdateConsumer();

}
