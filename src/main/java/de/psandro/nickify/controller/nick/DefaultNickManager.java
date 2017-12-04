package de.psandro.nickify.controller.nick;

import com.google.common.base.Preconditions;
import de.psandro.nickify.controller.exception.PlayerAlreadyNickedException;
import de.psandro.nickify.controller.exception.PlayerNotNickedException;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class DefaultNickManager implements NickManager {

    private final Map<UUID, Nickable> nickableMap = new HashMap<>();

    private final INickEntityFactory nickEntityFactory;
    private final Plugin plugin;

    public DefaultNickManager(INickEntityFactory factory, final Plugin plugin) {
        Preconditions.checkNotNull(factory);
        nickEntityFactory = factory;
        this.plugin = plugin;
    }


    @Nullable
    @Override
    public Nickable getNickable(UUID uuid) {
        return this.nickableMap.get(uuid);
    }

    @Override
    public Collection<Nickable> getNickables() {
        return this.nickableMap.values();
    }

    @Override
    public Nickable nick(Player player, String name) throws ExecutionException, InterruptedException {
        if (this.nickableMap.containsKey(player.getUniqueId()))
            throw new PlayerAlreadyNickedException(player.getName());
        final NickEntity entity = this.nickEntityFactory.getByName(name);
        final NickedPlayer nickedPlayer = new NickedPlayer(player, entity);
        this.nickableMap.put(player.getUniqueId(), nickedPlayer);
        this.updatePlayer(player);
        return nickedPlayer;
    }

    @Override
    public Nickable nick(Player player, UUID uuid) throws ExecutionException, InterruptedException {
        if (this.nickableMap.containsKey(player.getUniqueId()))
            throw new PlayerAlreadyNickedException(player.getName());
        final NickEntity entity = this.nickEntityFactory.getByUUID(uuid);
        final NickedPlayer nickedPlayer = new NickedPlayer(player, entity);
        this.nickableMap.put(player.getUniqueId(), nickedPlayer);
        this.updatePlayer(player);
        return nickedPlayer;
    }

    @Override
    public void unnick(Player player) {
        if (!this.nickableMap.containsKey(player.getUniqueId()))
            throw new PlayerNotNickedException(player.getName());
        final Nickable nickable = this.nickableMap.get(player.getUniqueId());
        this.nickableMap.remove(player.getUniqueId());
        this.updatePlayer(player);
    }

    private void updatePlayer(Player player) {
        final Collection<? extends Player> players = player.getWorld().getPlayers();
        players.stream().filter(p -> p.canSee(player)).forEach(p -> {
            p.hidePlayer(player);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!p.isOnline()) return;
                    p.showPlayer(player);
                }
            }.runTaskLater(this.plugin, 2);
        });
    }
}
