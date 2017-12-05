package de.psandro.nickify.controller.team;

import de.psandro.nickify.controller.team.wrapper.*;
import lombok.*;

import java.util.Collections;


@AllArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode
@Getter
@Setter
@ToString
public final class TeamView {

    private @NonNull
    String owner;
    private @NonNull
    String teamName;
    private @NonNull
    String prefix;
    private @NonNull
    String suffix;

    public CreateTeamPacket buildCreationPacket() {
        final CreateTeamPacket team = new CreateTeamPacket(this.teamName);
        team.setDisplayName(this.teamName);
        team.setPrefix(this.prefix);
        team.setSuffix(this.suffix);
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
        team.setPrefix(this.prefix);
        team.setSuffix(this.suffix);
        return team;
    }

}
