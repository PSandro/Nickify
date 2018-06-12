package de.psandro.nickify.model;

import com.comphenix.protocol.wrappers.WrappedGameProfile;
import de.psandro.nickify.controller.team.AdvancedNickEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;


@AllArgsConstructor
public final class CachedNickEntity implements AdvancedNickEntity {

    private final WrappedGameProfile fakeProfile;
    @NonNull
    @Getter
    private long latestUse = 0;

    @Override
    public String getName() {
        return this.fakeProfile.getName();
    }

    @Override
    public UUID getUniqueId() {
        return this.fakeProfile.getUUID();
    }


    @Override
    public WrappedGameProfile getFakeGameProfile() {
        return this.fakeProfile;
    }




    public static CachedNickEntity byAdvancedNickEntity(AdvancedNickEntity entity, long latestUse) {
        return new CachedNickEntity(entity.getFakeGameProfile(), latestUse);
    }
}
