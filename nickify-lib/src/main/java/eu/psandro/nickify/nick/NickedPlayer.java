package eu.psandro.nickify.nick;

import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.google.common.base.Preconditions;
import eu.psandro.nickify.team.Nickable;
import eu.psandro.nickify.team.TeamViewLayout;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.Set;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public final class NickedPlayer implements Nickable {

    private final PlayerData playerData;
    private final Set<UUID> exceptions;
    private WrappedGameProfile fakeGameProfile;
    private TeamViewLayout teamViewLayout;
    private String fakeName;

    NickedPlayer(final PlayerData nickifyPlayer, String nickname, TeamViewLayout layout, WrappedGameProfile gameProfile, @NonNull Set<UUID> exceptions) {
        Preconditions.checkNotNull(nickifyPlayer, "The player cannot be null!");
        this.playerData = nickifyPlayer;
        this.fakeName = nickname;
        this.teamViewLayout = layout;
        this.fakeGameProfile = gameProfile;
        this.exceptions = exceptions;
    }


    private static WrappedGameProfile buildCustomProfile(final UUID uniqueId, final Nickable nickEntity) {
        final WrappedGameProfile fakeGameProfile = new WrappedGameProfile(uniqueId, nickEntity.getFakeName());
        fakeGameProfile.getProperties().putAll(nickEntity.getFakeGameProfile().getProperties());
        return fakeGameProfile;
    }



    @Override
    public UUID getFakeUniqueId() {
        return this.fakeGameProfile.getUUID();
    }
}
