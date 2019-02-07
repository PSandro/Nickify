package eu.psandro.nickify.team;

import eu.psandro.nickify.team.wrapper.*;
import lombok.*;

import java.util.Arrays;

@ToString
@EqualsAndHashCode
public final class TeamView extends TeamViewLayout {

    @Getter
    private final @NonNull
    String ownerName;

    @Getter
    private final @NonNull
    String teamName;

    private final TeamPacket[] cache = new TeamPacket[5]; // creation - remove - update - memberAdd - memberRemove

    TeamView(String ownerName, String teamName, TeamViewLayout teamViewLayout) {
        super();
        teamViewLayout.applyOn(this);
        this.ownerName = ownerName;
        this.teamName = teamName;
    }

    private void clearCache() {
        Arrays.fill(this.cache, null);
    }

    CreateTeamPacket buildCreationPacket() {
        final int index = 0;
        if (this.cache[index] == null) {
            this.cache[index] = new CreateTeamPacket(
                    this.teamName, // team name & display name
                    super.getPrefix(), // prefix
                    super.getSuffix(), // suffix
                    this.getOwnerName() // ownerName
            );
        }
        return (CreateTeamPacket) this.cache[index];
    }

    RemoveTeamPacket buildRemovePacket() {
        final int index = 1;
        if (this.cache[index] == null) {
            this.cache[index] = new RemoveTeamPacket(
                    this.teamName // team name
            );
        }
        return (RemoveTeamPacket) this.cache[index];
    }

    UpdateTeamPacket buildUpdatePacket() {
        final int index = 2;
        if (this.cache[index] == null) {
            this.cache[index] = new UpdateTeamPacket(
                    this.teamName, // team name
                    super.getPrefix(), // prefix
                    super.getSuffix() // suffix
            );
        }
        return (UpdateTeamPacket) this.cache[index];
    }

    PlayerAddTeamPacket buildMemberAddPacket() {
        final int index = 3;
        if (this.cache[index] == null) {
            this.cache[index] = new PlayerAddTeamPacket(
                    this.teamName, // team name
                    this.getOwnerName() // ownerName
            );
        }
        return (PlayerAddTeamPacket) this.cache[index];
    }

    PlayerRemoveTeamPacket buildMemberRemovePacket() {
        final int index = 4;
        if (this.cache[index] == null) {
            this.cache[index] = new PlayerRemoveTeamPacket(
                    this.teamName, // team name
                    this.getOwnerName() // ownerName
            );
        }
        return (PlayerRemoveTeamPacket) this.cache[index];
    }


}
