package de.psandro.nickify.controller.nick;

import com.comphenix.protocol.wrappers.WrappedGameProfile;

import java.util.UUID;

public interface Nickable {

    String getRealName();

    NickEntity getNickEntity();

    UUID getUniqueId();

    WrappedGameProfile getFakeGameProfile();

}
