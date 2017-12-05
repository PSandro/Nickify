package de.psandro.nickify.controller.team;

import de.psandro.nickify.controller.nick.Nickable;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.entity.Player;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Getter
public final class TeamInfo {
    private final @NonNull
    Player owner;
    private @NonNull
    Optional<Nickable> nickable;
    private final @NonNull
    Set<ObserverEntity> observers;
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

    public void nick(final @NonNull Nickable nick) {
        this.nickable = Optional.of(nick);
        this.observers.forEach(observerEntity -> {
            if (!observerEntity.getUuid().equals(this.owner.getUniqueId()))
                observerEntity.changeName(nick.getNickEntity().getName());
        });
    }

    public void unnick() {
        this.nickable = Optional.empty();
        this.observers.forEach(observerEntity -> observerEntity.changeName(this.owner.getName()));
    }

    public void registerObserver(final ObserverEntity observerEntity) {
        if (this.observers.contains(observerEntity)) return;
        this.observers.add(observerEntity);
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
