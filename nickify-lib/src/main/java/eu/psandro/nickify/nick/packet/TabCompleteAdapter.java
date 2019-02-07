package eu.psandro.nickify.nick.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import eu.psandro.nickify.nick.NickProfile;
import lombok.NonNull;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;


public final class TabCompleteAdapter extends PacketAdapter {

    private final Cache<UUID, String[]> optionCache = CacheBuilder.newBuilder()
            .expireAfterWrite(3, TimeUnit.SECONDS)
            .build();

    private final @NonNull
    Set<NickProfile> nicks;

    public TabCompleteAdapter(final Plugin plugin, final @NonNull Set<NickProfile> nicks) {
        super(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.TAB_COMPLETE,
                PacketType.Play.Client.TAB_COMPLETE);
        this.nicks = nicks;
    }

    private Optional<NickProfile> checkNickName(String option) {
        return this.nicks.parallelStream().filter(nick ->
                nick.getFakeName().equalsIgnoreCase(option)
        ).findAny();
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        final Stream<String> options = Arrays.stream(event.getPacket().getStringArrays().read(0));
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
        if (text.length <= 0) return;
        final String current = text[text.length - 1].toLowerCase();
        final String[] options = this.nicks.parallelStream()
                .filter(nick -> nick.getFakeName().toLowerCase().startsWith(current))
                .map(NickProfile::getFakeName)
                .toArray(String[]::new);
        this.optionCache.put(event.getPlayer().getUniqueId(), options);
    }


}
