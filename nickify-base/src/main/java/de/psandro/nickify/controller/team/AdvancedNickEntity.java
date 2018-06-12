package de.psandro.nickify.controller.team;

import com.comphenix.protocol.wrappers.WrappedGameProfile;
import de.psandro.nickify.api.nick.NickEntity;

public interface AdvancedNickEntity extends NickEntity {

    WrappedGameProfile getFakeGameProfile();

}
