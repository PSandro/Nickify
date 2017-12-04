package de.psandro.nickify.controller.team;

import de.psandro.nickify.controller.team.wrapper.*;
import lombok.*;

import java.util.Arrays;


@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public final class TeamView {

    private @NonNull
    String owner;
    private @NonNull
    String teamName;
    private @NonNull
    String prefix;
    private @NonNull
    String suffix;
    private @NonNull
    NameTagVisibility nameTagVisibility;


    public CreateTeamPacket buildCreationPacket() {
        final CreateTeamPacket team = new CreateTeamPacket(this.teamName);
        team.setDisplayName(this.teamName);
        team.setNameTagVisibility(this.nameTagVisibility);
        team.setPrefix(this.prefix);
        team.setSuffix(this.suffix);
        return team;
    }

    public RemoveTeamPacket buildRemovePacket() {
        final RemoveTeamPacket team = new RemoveTeamPacket(this.teamName);
        return team;
    }

    public PlayerRemoveTeamPacket buildMemberRemovePacket() {
        final PlayerRemoveTeamPacket team = new PlayerRemoveTeamPacket(this.teamName);
        team.setPlayers(Arrays.asList(this.owner));
        return team;
    }

    public PlayerAddTeamPacket buildMemberAddPacket() {
        final PlayerAddTeamPacket team = new PlayerAddTeamPacket(this.teamName);
        team.setPlayers(Arrays.asList(this.owner));
        return team;
    }
    public UpdateTeamPacket buildUpdatePacket() {
        final UpdateTeamPacket team = new UpdateTeamPacket(this.teamName);
        team.setDisplayName(this.teamName);
        team.setNameTagVisibility(this.nameTagVisibility);
        team.setPrefix(this.prefix);
        team.setSuffix(this.suffix);
        return team;
    }

}
