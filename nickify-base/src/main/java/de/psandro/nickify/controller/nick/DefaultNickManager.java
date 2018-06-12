package de.psandro.nickify.controller.nick;

import com.google.common.base.Preconditions;
import de.psandro.nickify.api.exception.NickNameAlreadyInUse;
import de.psandro.nickify.api.exception.PlayerAlreadyNickedException;
import de.psandro.nickify.api.exception.PlayerNotNickedException;
import de.psandro.nickify.api.nick.NickEntity;
import de.psandro.nickify.controller.NickifyPlayer;
import de.psandro.nickify.controller.team.Nickable;
import de.psandro.nickify.controller.team.TeamNickUpdateConsumer;
import de.psandro.nickify.api.team.TeamViewLayout;
import de.psandro.nickify.model.CachedNickEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class DefaultNickManager implements NickManager {

    private final Map<UUID, Nickable> nickableMap = new HashMap<>();

    private final INickEntityFactory nickEntityFactory;
    private final Plugin plugin;
    private final TeamNickUpdateConsumer updateConsumer;

    public DefaultNickManager(INickEntityFactory factory, final Plugin plugin, TeamNickUpdateConsumer updateConsumer) {
        Preconditions.checkNotNull(factory);
        nickEntityFactory = factory;
        this.plugin = plugin;
        this.updateConsumer = updateConsumer;
    }


    @Override
    public Nickable getNickable(UUID uuid) {
        return this.nickableMap.get(uuid);
    }

    @Override
    public NickEntity getNickEntity(String nickname) {
        return this.nickableMap.values().parallelStream().filter(entity -> entity.getName().equalsIgnoreCase(nickname)).findAny().orElse(null);
    }

    @Override
    public NickEntity getNickEntity(UUID nickUniqueId) {
        return this.nickableMap.values().parallelStream().filter(entity -> entity.getUniqueId().equals(nickUniqueId)).findAny().orElse(null);
    }

    @Override
    public Collection<Nickable> getNickables() {
        return this.nickableMap.values();
    }

    @Override
    public Nickable nick(Player player, UUID uuid, TeamViewLayout layout) throws ExecutionException, InterruptedException {
        return this.nick(player, uuid, layout, new HashSet<>());
    }

    @Override
    public Nickable nick(Player player, UUID uuid, TeamViewLayout layout, Set<UUID> exceptions) throws ExecutionException, InterruptedException {
        if (this.nickableMap.containsKey(player.getUniqueId()))
            throw new PlayerAlreadyNickedException(player.getName());
        final NickEntity existing = this.getNickEntity(uuid);
        if (existing != null) throw new NickNameAlreadyInUse(existing.getName());

        final CachedNickEntity entity = this.nickEntityFactory.getByUUID(uuid);
        final NickedPlayer nickedPlayer = new NickedPlayer(new NickifyPlayer(player), entity.getName(), layout, entity.getFakeGameProfile(), exceptions);
        this.nickableMap.put(player.getUniqueId(), nickedPlayer);
        this.updatePlayer(player, nickedPlayer, exceptions);
        return nickedPlayer;
    }

    @Override
    public void unnick(Player player, Set<UUID> exceptions) {
        if (!this.nickableMap.containsKey(player.getUniqueId()))
            throw new PlayerNotNickedException(player.getName());
        final Nickable nickable = this.nickableMap.get(player.getUniqueId());
        this.nickableMap.remove(player.getUniqueId());
        this.updatePlayer(player, null, exceptions);
        this.nickEntityFactory.returnNickEntity(CachedNickEntity.byAdvancedNickEntity(nickable, System.currentTimeMillis()));
    }

    @Override
    public void unnick(Player player) {
        this.unnick(player, new HashSet<>());
    }

    private void updatePlayer(final Player player, final Nickable nickable, Set<UUID> exceptions) {
        final Collection<? extends Player> players = player.getWorld().getPlayers();
        players.stream().filter(p -> p.canSee(player)).forEach(p -> {
            p.hidePlayer(player);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!p.isOnline()) return;
                    p.showPlayer(player);
                    DefaultNickManager.this.updateConsumer.accept(NickifyPlayer.fromBukkitPlayer(player), nickable, exceptions);

                }
            }.runTaskLater(this.plugin, 2);
        });
    }
}
