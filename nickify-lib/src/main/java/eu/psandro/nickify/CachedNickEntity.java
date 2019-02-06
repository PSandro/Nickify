package eu.psandro.nickify;

import com.comphenix.protocol.wrappers.WrappedGameProfile;
import eu.psandro.nickify.team.AdvancedNickEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;


@AllArgsConstructor
public final class CachedNickEntity implements AdvancedNickEntity {

    private final WrappedGameProfile fakeProfile;
    @Getter
    private long latestUse = 0;

    @Override
    public String getFakeName() {
        return this.fakeProfile.getName();
    }

    @Override
    public UUID getFakeUniqueId() {
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
