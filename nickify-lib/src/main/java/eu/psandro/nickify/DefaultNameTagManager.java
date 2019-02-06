package eu.psandro.nickify;

import eu.psandro.nickify.nick.NickManager;
import eu.psandro.nickify.team.TeamManager;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public final class DefaultNameTagManager implements NameTagManager {

    private final TeamManager teamManager;
    private final NickManager nickManager;

}
