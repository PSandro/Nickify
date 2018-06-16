package de.psandro.nickify.controller.team;


import de.psandro.nickify.api.team.TeamInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public final class TeamController {

    private final @NonNull
    TeamInfo teamInfo;


    public void nick(final @NonNull Nickable nick, @NonNull Set<UUID> exceptions) {
        this.teamInfo.setNickEntity(Optional.of(nick));
        final TeamView nickTeamView = TeamViewFactory.createTeamView(nick.getFakeGameProfile().getName(), nick.getTeamViewLayout());
        this.teamInfo.getObservers().stream().filter(observerEntity -> !exceptions.contains(observerEntity)).forEach(observerEntity -> {
            if (!observerEntity.getUuid().equals(this.teamInfo.getOwner().getUniqueId())) {
                observerEntity.changeName(nickTeamView);
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
        this.teamInfo.setNickEntity(Optional.empty());
        this.teamInfo.getObservers().stream().filter(observerEntity -> !exceptions.contains(observerEntity)).forEach(observerEntity -> {
            //TODO maybe store previous teamview in observer entity and recall it
            observerEntity.changeName(this.teamInfo.getDefaultTeamView());
        });
    }

    protected void spawn() {
        this.teamInfo.getObservers().forEach(o -> {
            if (o instanceof ObserverEntity) {
                ((ObserverEntity) o).spawnView();
            } else {
                new ObserverEntity(o.getUuid(), o.getTeamView()).spawnView();
            }
        });
    }

    protected void update() {
        this.teamInfo.getObservers().forEach(o -> {
            if (o instanceof ObserverEntity) {
                ((ObserverEntity) o).updateView();
            } else {
                new ObserverEntity(o.getUuid(), o.getTeamView()).updateView();
            }
        });
    }

    protected void destroy() {
        this.teamInfo.getObservers().forEach(o -> {
            if (o instanceof ObserverEntity) {
                ((ObserverEntity) o).despawnView();
            } else {
                new ObserverEntity(o.getUuid(), o.getTeamView()).despawnView();
            }
        });
    }

}
