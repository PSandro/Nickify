package de.psandro.nickify.controller.team.listener;

import de.psandro.nickify.controller.NameTagManager;
import de.psandro.nickify.controller.team.ObserverEntity;
import de.psandro.nickify.controller.team.TeamInfo;
import de.psandro.nickify.controller.team.TeamView;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@AllArgsConstructor
public final class RegisterListener implements Listener {

    private final @NonNull
    NameTagManager nameTagManager;

    private final @NonNull
    Plugin plugin;

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final TeamView teamView = this.nameTagManager.getTeamManager().getTeamViewFactory().createTeamView(player.getName(), "trator ", " trator", 1);
        System.out.println(teamView.toString());
        final TeamInfo teamInfo = this.nameTagManager.getTeamManager().createTeamInfo(player, teamView, new LinkedHashSet(Bukkit.getOnlinePlayers().stream().map(all -> new ObserverEntity(all.getUniqueId(), teamView, false)).collect(Collectors.toSet())));
        player.sendMessage("TeamInfo initialisiert! Observers: " + teamInfo.getObservers().size());
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            this.nameTagManager.getTeamManager().spawnTeam(teamInfo);
            player.sendMessage("Team erschaffen!");
        }, 2);


        this.nameTagManager.getTeamManager().getTeamInfos().forEach(info -> {
            final ObserverEntity observerEntity = new ObserverEntity(player.getUniqueId(), info.getDefaultTeamView(), false);
            info.getObservers().add(observerEntity);
            observerEntity.spawnView();
        });

    }

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final TeamInfo teamView = this.nameTagManager.getTeamManager().getTeamInfo(player.getUniqueId());
        if (teamView == null) return;
        this.nameTagManager.getTeamManager().deleteTeam(teamView);
    }
}
