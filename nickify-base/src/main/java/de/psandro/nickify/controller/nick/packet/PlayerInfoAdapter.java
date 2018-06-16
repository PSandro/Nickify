package de.psandro.nickify.controller.nick.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import de.psandro.nickify.controller.nick.NickManager;
import de.psandro.nickify.controller.team.Nickable;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


public final class PlayerInfoAdapter extends PacketAdapter {

    private final NickManager nickManager;

    public PlayerInfoAdapter(final Plugin plugin, final NickManager nickManager) {
        super(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.PLAYER_INFO);
        this.nickManager = nickManager;
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
                    final Nickable nickable = this.nickManager.getNickable(uuid);
                    if (event.getPlayer().getUniqueId().equals(uuid)) return playerInfoData;

                    if (Bukkit.getPlayer(uuid) == null || nickable == null) return playerInfoData;
                    else {
                        System.out.println("Faked profile: original= " + playerInfoData.getProfile().getName() + " fake= " + nickable.getFakeName());
                        return new PlayerInfoData(
                                nickable.getFakeGameProfile(),
                                playerInfoData.getLatency(),
                                playerInfoData.getGameMode(),
                                playerInfoData.getDisplayName()
                        );
                    }
                }).collect(Collectors.toList());

        event.getPacket().getPlayerInfoDataLists().write(0, fakePlayerInfoDataList);
    }
}
