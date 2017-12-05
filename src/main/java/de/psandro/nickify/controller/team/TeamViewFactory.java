package de.psandro.nickify.controller.team;

import de.psandro.nickify.controller.team.wrapper.NameTagVisibility;

public interface TeamViewFactory {

    TeamView createTeamView(String owner, String prefix, String suffix, int priority, NameTagVisibility nameTagVisibility);

    TeamView createTeamView(String owner, String prefix, String suffix, int priority);

}
