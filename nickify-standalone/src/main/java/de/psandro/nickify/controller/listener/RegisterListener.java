package de.psandro.nickify.controller.listener;

import de.psandro.nickify.controller.NameTagManager;
import de.psandro.nickify.controller.team.*;
import de.psandro.nickify.misc.TeamViewFetcher;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@AllArgsConstructor
public final class RegisterListener implements Listener {

    private final @NonNull
    NameTagManager nameTagManager;
    private final @NonNull
    TeamViewFetcher teamViewFetcher;

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final TeamView teamView = teamViewFetcher.getTeamView(player);
        final LinkedHashSet observerEntities = new LinkedHashSet(Bukkit.getOnlinePlayers().stream().map(all -> new ObserverEntity(all.getUniqueId(), teamView, false)).collect(Collectors.toSet()));
        final TeamController controller = this.nameTagManager.getTeamManager().createTeamController(player, teamView, observerEntities);
        this.nameTagManager.getTeamManager().spawnTeam(controller);

        this.nameTagManager.getTeamManager().getTeamInfos().forEach(info -> {
            final ObserverEntity observerEntity = new ObserverEntity(player.getUniqueId(), info.getDefaultActiveTeamViewLayout(), false);
            info.getObservers().add(observerEntity);
            observerEntity.spawnView();
        });

    }

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final TeamController teamView = this.nameTagManager.getTeamManager().getTeamController(player.getUniqueId());
        if (teamView == null) return;
        this.nameTagManager.getTeamManager().deleteTeam(teamView.getTeamInfo());
        if (Bukkit.getOnlinePlayers().size() <= 1) {
            TeamViewFactory.resetCounter();
        }
    }
}
