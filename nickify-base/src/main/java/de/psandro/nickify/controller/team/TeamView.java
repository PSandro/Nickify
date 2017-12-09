package de.psandro.nickify.controller.team;

import de.psandro.nickify.controller.team.wrapper.*;
import lombok.*;

import java.util.Collections;


@EqualsAndHashCode
@Getter
@Setter
@ToString
public final class TeamView extends TeamViewLayout {

    private @NonNull
    String owner;
    private @NonNull
    String teamName;

    TeamView(String prefix, String suffix, int priority, String owner, String teamName) {
        super(prefix, suffix, priority);
        this.owner = owner;
        this.teamName = teamName;
    }

    TeamView(TeamView teamView) {
        this(teamView.getPrefix(), teamView.getSuffix(), teamView.getPriority(), teamView.getOwner(), teamView.getTeamName());
    }

    TeamView(TeamViewLayout teamViewLayout, String owner, String teamName) {
        this(teamViewLayout.getPrefix(), teamViewLayout.getSuffix(), teamViewLayout.getPriority(), owner, teamName);
    }

    public CreateTeamPacket buildCreationPacket() {
        final CreateTeamPacket team = new CreateTeamPacket(this.teamName);
        team.setDisplayName(this.teamName);
        team.setPrefix(super.getPrefix());
        team.setSuffix(super.getSuffix());
        team.setPlayers(Collections.singletonList(this.owner));
        return team;
    }

    public RemoveTeamPacket buildRemovePacket() {
        return new RemoveTeamPacket(this.teamName);
    }

    public PlayerRemoveTeamPacket buildMemberRemovePacket() {
        final PlayerRemoveTeamPacket team = new PlayerRemoveTeamPacket(this.teamName);
        team.setPlayers(Collections.singletonList(this.owner));
        return team;
    }

    public PlayerAddTeamPacket buildMemberAddPacket() {
        final PlayerAddTeamPacket team = new PlayerAddTeamPacket(this.teamName);
        team.setPlayers(Collections.singletonList(this.owner));
        return team;
    }

    public UpdateTeamPacket buildUpdatePacket() {
        final UpdateTeamPacket team = new UpdateTeamPacket(this.teamName);
        team.setDisplayName(this.teamName);
        team.setPrefix(super.getPrefix());
        team.setSuffix(super.getSuffix());
        return team;
    }

}
