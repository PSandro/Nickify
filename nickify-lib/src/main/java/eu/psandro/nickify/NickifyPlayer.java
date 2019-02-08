package eu.psandro.nickify;

import eu.psandro.nickify.team.TeamViewLayout;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.bukkit.entity.Player;

import java.util.UUID;


@AllArgsConstructor(access = AccessLevel.MODULE)
@EqualsAndHashCode
public final class NickifyPlayer {

    private transient final @NonNull
    Player player;

    private final @NonNull
    Tag tag;

    private final Tag nickTag;

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

    public void setLayout(@NonNull TeamViewLayout layout) {
        layout.applyOn(this.tag.getTeamView());
        this.update();
    }

    public void setPrefix(String prefix) {
        this.tag.getTeamView().setPrefix(prefix);
        this.update();
    }

    public void setSuffix(String suffix) {
        this.tag.getTeamView().setSuffix(suffix);
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
