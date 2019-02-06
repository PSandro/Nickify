package eu.psandro.nickify.team;

import lombok.*;

import java.util.UUID;

@Getter
@EqualsAndHashCode
public abstract class AbstractObserver {

    private final @NonNull
    UUID uuid;
    private @NonNull @Setter(AccessLevel.PROTECTED)
    AssignedTeamViewLayout teamView;
    @Setter
    private boolean ignoreNameChange;

    public AbstractObserver(UUID uuid, AssignedTeamViewLayout teamView, boolean ignoreNameChange) {
        this.uuid = uuid;
        this.teamView = teamView;
        this.ignoreNameChange = ignoreNameChange;
    }

    public void changeName(AssignedTeamViewLayout teamView) {
        this.changeName(teamView, false);
    }

    public abstract void changeName(AssignedTeamViewLayout teamView, boolean force);

}
