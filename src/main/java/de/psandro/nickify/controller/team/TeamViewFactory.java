package de.psandro.nickify.controller.team;

public interface TeamViewFactory {

    TeamView createTeamView(String owner, String prefix, String suffix, int priority);

}
