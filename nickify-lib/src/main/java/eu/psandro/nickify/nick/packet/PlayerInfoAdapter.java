package eu.psandro.nickify.nick.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import eu.psandro.nickify.nick.NickProfile;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


public final class PlayerInfoAdapter extends PacketAdapter {

    @Getter
    private final @NonNull
    Set<NickProfile> nicks;

    public PlayerInfoAdapter(final Plugin plugin, final @NonNull Set<NickProfile> nicks) {
        super(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.PLAYER_INFO);
        this.nicks = nicks;
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        final EnumWrappers.PlayerInfoAction infoAction = event.getPacket().getPlayerInfoAction().read(0);
        if (infoAction != EnumWrappers.PlayerInfoAction.ADD_PLAYER) return;


        final List<PlayerInfoData> playerInfoDataList = event.getPacket().getPlayerInfoDataLists().read(0);
        final List<PlayerInfoData> fakePlayerInfoDataList = playerInfoDataList
                .stream()
                .map(playerInfoData -> {
                    final UUID uuid = playerInfoData.getProfile().getUUID();

                    if (event.getPlayer().getUniqueId().equals(uuid)) return playerInfoData;

                    final NickProfile nick = this.nicks.parallelStream().filter(
                            other -> other.getFakeUniqueId().equals(uuid)
                    ).findAny().orElse(null);

                    if (nick == null || Bukkit.getPlayer(uuid) == null) return playerInfoData;

                    return new PlayerInfoData(
                            nick.getFakeGameProfile(),
                            playerInfoData.getLatency(),
                            playerInfoData.getGameMode(),
                            playerInfoData.getDisplayName()
                    );
                }).collect(Collectors.toList());

        event.getPacket().getPlayerInfoDataLists().write(0, fakePlayerInfoDataList);
    }
}
