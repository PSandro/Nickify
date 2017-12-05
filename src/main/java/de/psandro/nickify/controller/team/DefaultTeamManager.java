package de.psandro.nickify.controller.team;

import de.psandro.nickify.controller.nick.Nickable;
import org.bukkit.entity.Player;

import java.util.*;

public final class DefaultTeamManager implements TeamManager {

    private final Map<UUID, TeamInfo> teamBindings = new HashMap<>();

    public DefaultTeamManager() {
    }

    @Override
    public TeamInfo createTeamInfo(Player player, TeamView defaultTeamView, LinkedHashSet<ObserverEntity> observers, Nickable nickable) {
        return null;
    }

    @Override
    public TeamInfo createTeamInfo(Player player, TeamView defaultTeamView, LinkedHashSet<ObserverEntity> observers) {
        return null;
    }

    @Override
    public void spawnTeam(TeamInfo teamInfo) {
        if (this.teamBindings.containsKey(teamInfo.getOwner().getUniqueId())) return; //TODO throw exception

    }

    @Override
    public void updateTeam(TeamInfo teamInfo) {

    }

    @Override
    public TeamInfo getTeamInfo(UUID uuid) {
        return null;
    }

    @Override
    public TeamNickUpdateConsumer getUpdateConsumer() {
        return (player, nickable) -> {
            final TeamInfo teamInfo = this.getTeamInfo(player.getUniqueId());
            if (teamInfo == null) return;
            teamInfo.nick(nickable);
        };
    }
}
