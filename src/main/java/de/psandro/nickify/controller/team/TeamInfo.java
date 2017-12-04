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
    private final @NonNull Player owner;
    private @NonNull Optional<Nickable> nickable;
    private final @NonNull Set<ObserverEntity> observers;
    private @NonNull TeamView defaultTeamView;

    public TeamInfo(final Player owner, LinkedHashSet<ObserverEntity> observers, TeamView defaultTeamView, Optional<Nickable> nickable) {
        this.owner = owner;
        this.observers = observers;
        this.defaultTeamView = defaultTeamView;
        this.nickable = nickable;
    }
    public TeamInfo(final Player owner, LinkedHashSet<ObserverEntity> observers, TeamView defaultTeamView) {
        this(owner, observers, defaultTeamView, Optional.empty());
    }

    public void nick(final @NonNull Nickable nick) {
        this.nickable = Optional.of(nick);
        this.observers.forEach(observerEntity -> observerEntity.nameChange(nick.getNickName()));
    }

}
