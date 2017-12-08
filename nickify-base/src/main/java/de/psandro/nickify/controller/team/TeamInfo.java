package de.psandro.nickify.controller.team;

import de.psandro.nickify.controller.nick.Nickable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
public final class TeamInfo {
    private final @NonNull
    Player owner;
    private @NonNull
    Optional<Nickable> nickable;
    private final @NonNull
    Set<ObserverEntity> observers;
    @Setter(AccessLevel.PACKAGE)
    private @NonNull
    TeamView defaultTeamView;

    protected TeamInfo(final Player owner, LinkedHashSet<ObserverEntity> observers, TeamView defaultTeamView, Nickable nickable) {
        this.owner = owner;
        this.observers = observers;
        this.defaultTeamView = defaultTeamView;
        this.nickable = Optional.ofNullable(nickable);
    }

    protected TeamInfo(final Player owner, LinkedHashSet<ObserverEntity> observers, TeamView defaultTeamView) {
        this(owner, observers, defaultTeamView, null);
    }

    public void nick(final @NonNull Nickable nick, @NonNull Set<UUID> exceptions) {
        this.nickable = Optional.of(nick);
        this.observers.stream().filter(observerEntity -> !exceptions.contains(observerEntity)).forEach(observerEntity -> {
            if (!observerEntity.getUuid().equals(this.owner.getUniqueId())) {
                observerEntity.updateTeamView(nick.getTeamViewLayout());
                observerEntity.updateView();
                observerEntity.changeName(nick.getNickEntity().getName());
            }

        });
    }

    public void nick(final @NonNull Nickable nick) {
        this.nick(nick, new HashSet<>());
    }

    public void unnick() {
        this.unnick(new HashSet<>());
    }

    public void unnick(@NonNull Set<UUID> exceptions) {
        this.nickable = Optional.empty();
        this.observers.stream().filter(observerEntity -> !exceptions.contains(observerEntity)).forEach(observerEntity -> {
            //TODO maybe store previous teamview in observer entity and recall it
            observerEntity.changeName(this.owner.getName());
            observerEntity.updateTeamView(this.defaultTeamView);
            observerEntity.updateView();
        });
    }

    public void registerObserver(final ObserverEntity observerEntity) {
        if (this.observers.contains(observerEntity)) return;
        this.observers.add(observerEntity);
    }

    public TeamViewLayout getDefaultTeamViewLayout() {
        return this.defaultTeamView;
    }

    public TeamView getDefaultActiveTeamView() {
        if (!this.nickable.isPresent()) return new TeamView(this.defaultTeamView);
        final Nickable nickable = this.nickable.get();
        return new TeamView(nickable.getTeamViewLayout(), nickable.getNickEntity().getName(), this.defaultTeamView.getTeamName());
    }

    protected void spawn() {
        this.observers.forEach(ObserverEntity::spawnView);
    }

    protected void update() {
        this.observers.forEach(ObserverEntity::updateView);
    }

    protected void destroy() {
        this.getObservers().forEach(ObserverEntity::despawnView);
    }

}
