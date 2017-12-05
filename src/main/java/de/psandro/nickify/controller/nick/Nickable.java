package de.psandro.nickify.controller.nick;

import com.comphenix.protocol.wrappers.WrappedGameProfile;
import de.psandro.nickify.controller.team.TeamView;
import de.psandro.nickify.controller.team.TeamViewLayout;

import java.util.UUID;

public interface Nickable {

    String getRealName();

    NickEntity getNickEntity();

    UUID getUniqueId();

    WrappedGameProfile getFakeGameProfile();

    TeamViewLayout getTeamViewLayout();

}
