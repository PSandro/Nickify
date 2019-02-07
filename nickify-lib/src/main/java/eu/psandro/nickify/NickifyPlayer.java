package eu.psandro.nickify;

import eu.psandro.nickify.nick.PlayerData;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.bukkit.entity.Player;

import java.util.UUID;


@AllArgsConstructor
@EqualsAndHashCode
public final class NickifyPlayer implements PlayerData {

    private transient final @NonNull
    Player player;

    public Player getPlayer() {
        return this.player;
    }

    @Override
    public String getRealName() {
        return this.player.getName();
    }

    @Override
    public UUID getUniqueId() {
        return this.player.getUniqueId();
    }

    public void nick(String nickname) {
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

    private void update() {
        //TODO: implement this
    }


    public static NickifyPlayer fromBukkitPlayer(@NonNull final Player player) {
        return new NickifyPlayer(player);
    }

}
