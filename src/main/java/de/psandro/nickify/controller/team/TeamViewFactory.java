package de.psandro.nickify.controller.team;

public interface TeamViewFactory {

    TeamView createTeamView(String owner, TeamViewLayout layout, int priority);


    void resetCounter();

}
