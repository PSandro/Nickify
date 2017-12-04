package de.psandro.nickify.controller.nick.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import de.psandro.nickify.controller.nick.NickManager;
import de.psandro.nickify.controller.nick.Nickable;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;


public final class TabCompleteAdapter extends PacketAdapter {

    private final NickManager nickManager;
    private final Cache<UUID, String[]> optionCache = CacheBuilder.newBuilder()
            .expireAfterWrite(500, TimeUnit.MILLISECONDS)
            .build();

    public TabCompleteAdapter(final Plugin plugin, final NickManager nickManager) {
        super(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.TAB_COMPLETE,
                PacketType.Play.Client.TAB_COMPLETE);
        this.nickManager = nickManager;
    }

    private Optional<Nickable> checkNickName(String option) {
        return this.nickManager.getNickables().stream().filter(nickable ->
                nickable.getNickName().equalsIgnoreCase(option)
        ).findFirst();
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        final Stream<String> options = Arrays.asList(event.getPacket().getStringArrays().read(0)).stream();
        String[] fakeOptions = options.filter(option -> !this.checkNickName(option).isPresent()).toArray(String[]::new);
        final String[] cachedOptions = this.optionCache.getIfPresent(event.getPlayer().getUniqueId());
        if (!(cachedOptions == null)) {
            fakeOptions = (String[]) ArrayUtils.addAll(cachedOptions, fakeOptions);
        }
        event.getPacket().getStringArrays().write(0, fakeOptions);
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        final String[] text = event.getPacket().getStrings().read(0).split(" ");
        if (text == null || text.length <= 0) return;
        final String current = text[text.length-1].toLowerCase();
        final String[] options = this.nickManager.getNickables().stream()
                .filter(nickable -> nickable.getNickName().toLowerCase().startsWith(current))
                .map(Nickable::getNickName)
                .toArray(String[]::new);
        this.optionCache.put(event.getPlayer().getUniqueId(), options);
    }


}
