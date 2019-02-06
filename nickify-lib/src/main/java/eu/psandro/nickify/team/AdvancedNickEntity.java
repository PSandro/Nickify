package eu.psandro.nickify.team;

import com.comphenix.protocol.wrappers.WrappedGameProfile;
import eu.psandro.nickify.nick.NickEntity;

public interface AdvancedNickEntity extends NickEntity {

    WrappedGameProfile getFakeGameProfile();

}
