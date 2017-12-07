package de.psandro.nickify.controller.nick;

import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.google.common.base.Preconditions;
import de.psandro.nickify.controller.team.TeamViewLayout;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public final class NickedPlayer implements Nickable {

    private final String realName;
    private final NickEntity nickEntity;
    private final UUID uniqueId;
    private final WrappedGameProfile fakeGameProfile;
    private final Set<UUID> exceptions;
    private final TeamViewLayout teamViewLayout;

    NickedPlayer(final Player player, final NickEntity nickEntity, @NonNull Set<UUID> exceptions, @NonNull TeamViewLayout teamViewLayout) {
        Preconditions.checkNotNull(player, "The player cannot be null!");
        Preconditions.checkNotNull(nickEntity, "The Nick Entity cannot be null!");
        this.realName = player.getName();
        this.nickEntity = nickEntity;
        this.uniqueId = player.getUniqueId();
        this.fakeGameProfile = NickedPlayer.buildCustomProfile(player.getUniqueId(), nickEntity);
        this.exceptions = exceptions;
        this.teamViewLayout = teamViewLayout;
    }


    private static final WrappedGameProfile buildCustomProfile(final UUID uniqueId, final NickEntity nickEntity) {
        final WrappedGameProfile fakeGameProfile = new WrappedGameProfile(uniqueId, nickEntity.getName());
        fakeGameProfile.getProperties().putAll(nickEntity.getProfileProperties());
        return fakeGameProfile;
    }

}
