package eu.psandro.nickify.team;

import eu.psandro.nickify.nick.NickEntity;
import eu.psandro.nickify.nick.PlayerData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.*;

@Getter
public class TeamInfo {
    private final @NonNull
    PlayerData owner;
    private @NonNull
    Optional<NickEntity> nickEntity;
    @Getter
    private final @NonNull
    Set<AbstractObserver> observers;
    @Setter(AccessLevel.PACKAGE)
    private @NonNull
    AssignedTeamViewLayout defaultTeamView;

    public TeamInfo(final PlayerData owner, LinkedHashSet<AbstractObserver> observers, AssignedTeamViewLayout defaultTeamView, NickEntity nickEntity) {
        this.owner = owner;
        this.observers = observers;
        this.defaultTeamView = defaultTeamView;
        this.nickEntity = Optional.ofNullable(nickEntity);
    }

    public TeamInfo(final PlayerData owner, LinkedHashSet<AbstractObserver> observers, AssignedTeamViewLayout defaultTeamView) {
        this(owner, observers, defaultTeamView, null);
    }

    public void setNickEntity(@NonNull Optional<NickEntity> nickEntity) {
        this.nickEntity = nickEntity;
    }

    public void registerObserver(final AbstractObserver observerEntity) {
        if (this.observers.contains(observerEntity)) return;
        this.observers.add(observerEntity);
    }

    public TeamViewLayout getDefaultTeamViewLayout() {
        return this.defaultTeamView;
    }

    public AssignedTeamViewLayout getDefaultActiveTeamViewLayout() {
        if (!this.nickEntity.isPresent()) return this.defaultTeamView;
        final NickEntity nickEntity = this.nickEntity.get();
        return this.defaultTeamView.buildCustomAssignment(nickEntity.getFakeName(), this.defaultTeamView.getTeamName());
    }


}
