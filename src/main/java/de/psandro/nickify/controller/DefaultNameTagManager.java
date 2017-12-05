package de.psandro.nickify.controller;

import de.psandro.nickify.controller.nick.NickManager;
import de.psandro.nickify.controller.team.TeamManager;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public final class DefaultNameTagManager implements NameTagManager {

    private final TeamManager teamManager;
    private final NickManager nickManager;

}
