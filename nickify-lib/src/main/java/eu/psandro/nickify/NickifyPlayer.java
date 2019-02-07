package eu.psandro.nickify;

import eu.psandro.nickify.nick.NickProcessor;
import eu.psandro.nickify.team.TeamProcessor;
import eu.psandro.nickify.team.TeamViewLayout;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.bukkit.entity.Player;

import java.util.UUID;


@AllArgsConstructor
@EqualsAndHashCode
public final class NickifyPlayer {

    private final @NonNull
    NickProcessor nickProcessor;
    private final @NonNull
    TeamProcessor teamProcessor;

    private transient final @NonNull
    Player player;

    public String getRealName() {
        return this.player.getName();
    }

    public UUID getUniqueId() {
        return this.player.getUniqueId();
    }

    public void nick(String nickname, TeamViewLayout layout) {
        //TODO: implement this
        this.update();
    }

    public void setLayout(TeamViewLayout layout) {
        //TODO: implement this
        this.update();
    }

    public void setPrefix(String nickname) {
        //TODO: implement this
        this.update();
    }

    public void setSuffix(String nickname) {
        //TODO: implement this
        this.update();
    }


    public void update() {
        //TODO: implement this

        /*final Collection<? extends Player> players = player.getWorld().getPlayers();
        players.stream().filter(p -> p.canSee(player)).forEach(p -> {
            p.hidePlayer(player);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!p.isOnline()) return;
                    p.showPlayer(player);

                }
            }.runTaskLater(this.plugin, 2);
        });*/
    }
}
