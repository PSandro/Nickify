package de.psandro.nickify.controller;

import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public final class NickedPlayer implements Nickable {

    private final String realName;
    private final String nickName;
    private final UUID uniqueId;
    private final WrappedGameProfile fakeGameProfile;

    NickedPlayer(final Player player, final NickEntity nickEntity) {
        Preconditions.checkNotNull(player, "The player cannot be null!");
        Preconditions.checkNotNull(nickEntity, "The Nick Entity cannot be null!");
        this.realName = player.getName();
        this.nickName = nickEntity.getName();
        this.uniqueId = player.getUniqueId();
        this.fakeGameProfile = NickedPlayer.buildCustomProfile(player.getUniqueId(), nickEntity);
    }


    private static final WrappedGameProfile buildCustomProfile(final UUID uniqueId, final NickEntity nickEntity) {
        final WrappedGameProfile fakeGameProfile = new WrappedGameProfile(uniqueId, nickEntity.getName());
        fakeGameProfile.getProperties().putAll(nickEntity.getProfileProperties());
        return fakeGameProfile;
    }

}
