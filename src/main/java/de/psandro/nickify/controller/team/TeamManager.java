package de.psandro.nickify.controller.team;

import de.psandro.nickify.controller.nick.Nickable;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.UUID;

public interface TeamManager {

    TeamInfo createTeamInfo(Player player, TeamView defaultTeamView, LinkedHashSet<ObserverEntity> observers, Nickable nickable);

    TeamInfo createTeamInfo(Player player, TeamView defaultTeamView, LinkedHashSet<ObserverEntity> observers);

    void spawnTeam(TeamInfo teamInfo);

    void deleteTeam(TeamInfo teamInfo);

    Collection<TeamInfo> getTeamInfos();

    void updateTeam(TeamInfo teamInfo);

    TeamInfo getTeamInfo(UUID uuid);

    TeamNickUpdateConsumer getUpdateConsumer();

    TeamViewFactory getTeamViewFactory();
}
