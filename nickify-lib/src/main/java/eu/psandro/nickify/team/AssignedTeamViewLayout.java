package eu.psandro.nickify.team;

import lombok.*;

@EqualsAndHashCode
@Getter
@Setter
@ToString

public abstract class AssignedTeamViewLayout extends TeamViewLayout {

    private @NonNull
    String owner;
    private @NonNull
    String teamName;

    protected AssignedTeamViewLayout(String prefix, String suffix, int priority, String owner, String teamName) {
        super(prefix, suffix, priority);
        this.owner = owner;
        this.teamName = teamName;
    }


    protected AssignedTeamViewLayout(TeamViewLayout teamViewLayout, String owner, String teamName) {
        this(teamViewLayout.getPrefix(), teamViewLayout.getSuffix(), teamViewLayout.getPriority(), owner, teamName);
    }


    public abstract AssignedTeamViewLayout buildCustomAssignment(final String owner, final String teamName);

}
