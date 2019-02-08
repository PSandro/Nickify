package eu.psandro.nickify.nick;

import com.comphenix.protocol.wrappers.WrappedGameProfile;

/**
 * @author PSandro on 07.02.19
 * @project Nickify
 */
public interface NickProfile extends Nick {

    WrappedGameProfile getFakeGameProfile();

}
