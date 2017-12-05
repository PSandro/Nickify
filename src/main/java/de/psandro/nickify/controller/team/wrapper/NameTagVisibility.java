package de.psandro.nickify.controller.team.wrapper;

public enum NameTagVisibility {
    ALWAYS("always"),
    HIDE_FOR_OTHER_TEAMS("hideForOtherTeams"),
    HIDE_FOR_OWN_TEAM("hideForOwnTeam"),
    NEVER("never");

    private final String name;
    NameTagVisibility(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }
}
