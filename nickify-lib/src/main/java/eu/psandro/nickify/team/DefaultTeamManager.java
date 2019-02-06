package eu.psandro.nickify.team;

import eu.psandro.nickify.NickifyPlayer;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
public final class DefaultTeamManager implements TeamManager {

    private final Map<UUID, TeamController> teamBindings = new HashMap<>();

    @Override
    public TeamController createTeamController(Player player, TeamView defaultTeamView, LinkedHashSet<AbstractObserver> observers, Nickable nickable) {
        return new TeamController(new TeamInfo(
                new NickifyPlayer(player),
                observers,
                defaultTeamView,
                nickable
        ));
    }

    @Override
    public TeamController createTeamController(Player player, TeamView defaultTeamView, LinkedHashSet<AbstractObserver> observers) {
        return this.createTeamController(player, defaultTeamView, observers, null);
    }

    @Override
    public void spawnTeam(TeamController teamInfo) {
        if (this.teamBindings.containsKey(teamInfo.getTeamInfo().getOwner().getUniqueId()))
            return; //TODO throw exception
        teamInfo.spawn();
        this.teamBindings.put(teamInfo.getTeamInfo().getOwner().getUniqueId(), teamInfo);
    }

    @Override
    public void deleteTeam(TeamInfo teamInfo) {
        TeamController controller = this.teamBindings.get(teamInfo.getOwner().getUniqueId());
        if (controller == null) return; //TODO throw exception
        controller.destroy();
        this.teamBindings.remove(teamInfo.getOwner().getUniqueId());
    }

    @Override
    public Collection<TeamInfo> getTeamInfos() {
        return this.teamBindings.values().stream().map(TeamController::getTeamInfo).collect(Collectors.toSet());
    }

    @Override
    public void updateTeam(TeamInfo teamInfo) {
        TeamController controller = this.teamBindings.get(teamInfo.getOwner().getUniqueId());
        if (controller == null) return; //TODO throw exception
        controller.update();
    }

    @Override
    public TeamController getTeamController(UUID uuid) {
        return this.teamBindings.get(uuid);
    }


    @Override
    public TeamNickUpdateConsumer getUpdateConsumer() {
        return (player, nickable, exceptions) -> {
            final TeamController teamController = this.getTeamController(player.getUniqueId());
            if (teamController == null) return;
            if (nickable == null) {
                teamController.unnick();
            } else {
                teamController.nick(nickable, exceptions);
            }

        };
    }

}
