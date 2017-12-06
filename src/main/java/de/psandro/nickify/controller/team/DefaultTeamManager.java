package de.psandro.nickify.controller.team;

import de.psandro.nickify.controller.nick.Nickable;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.*;

public final class DefaultTeamManager implements TeamManager {

    private final Map<UUID, TeamInfo> teamBindings = new HashMap<>();
    private final TeamViewFactory viewFactory = new DefaultTeamViewFactory();

    public DefaultTeamManager() {
    }

    @Override
    public TeamInfo createTeamInfo(Player player, TeamView defaultTeamView, LinkedHashSet<ObserverEntity> observers, Nickable nickable) {
        return new TeamInfo(player, observers, defaultTeamView, nickable);
    }

    @Override
    public TeamInfo createTeamInfo(Player player, TeamView defaultTeamView, LinkedHashSet<ObserverEntity> observers) {
        return this.createTeamInfo(player, defaultTeamView, observers, null);
    }

    @Override
    public void spawnTeam(TeamInfo teamInfo) {
        if (this.teamBindings.containsKey(teamInfo.getOwner().getUniqueId())) return; //TODO throw exception
        teamInfo.spawn();
        this.teamBindings.put(teamInfo.getOwner().getUniqueId(), teamInfo);
    }

    @Override
    public void deleteTeam(TeamInfo teamInfo) {
        if (!this.teamBindings.containsKey(teamInfo.getOwner().getUniqueId())) return; //TODO throw exception
        teamInfo.destroy();
        this.teamBindings.remove(teamInfo.getOwner().getUniqueId());
    }

    @Override
    public Collection<TeamInfo> getTeamInfos() {
        return this.teamBindings.values();
    }

    @Override
    public void updateTeam(TeamInfo teamInfo) {
        if (!this.teamBindings.containsKey(teamInfo.getOwner().getUniqueId())) return; //TODO throw exception
        teamInfo.update();
    }

    @Override
    @Nullable
    public TeamInfo getTeamInfo(UUID uuid) {
        return this.teamBindings.get(uuid);
    }

    @Override
    public TeamNickUpdateConsumer getUpdateConsumer() {
        return (player, nickable, exceptions) -> {
            final TeamInfo teamInfo = this.getTeamInfo(player.getUniqueId());
            if (teamInfo == null) return;
            if (nickable == null) {
                teamInfo.unnick();
            } else {
                teamInfo.nick(nickable, exceptions);
            }

        };
    }

    @Override
    public TeamViewFactory getTeamViewFactory() {
        return this.viewFactory;
    }
}
