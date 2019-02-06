package eu.psandro.nickify.team;

import eu.psandro.nickify.team.wrapper.*;
import lombok.*;


@EqualsAndHashCode
@Getter
@Setter
@ToString
public final class TeamView extends AssignedTeamViewLayout {


    public TeamView(String prefix, String suffix, int priority, String owner, String teamName) {
        super(prefix, suffix, priority, owner, teamName);
    }

    TeamView(TeamViewLayout teamViewLayout, String owner, String teamName) {
        super(teamViewLayout, owner, teamName);
    }

    @Override
    public AssignedTeamViewLayout buildCustomAssignment(String owner, String teamName) {
        return new TeamView(this.getPrefix(), this.getSuffix(), this.getPriority(), owner, teamName);
    }

    public CreateTeamPacket buildCreationPacket() {
        return TeamViewPacketBuilder.buildCreationPacket(this);
    }

    public RemoveTeamPacket buildRemovePacket() {
        return TeamViewPacketBuilder.buildRemovePacket(this);
    }

    public PlayerRemoveTeamPacket buildMemberRemovePacket() {
        return TeamViewPacketBuilder.buildMemberRemovePacket(this);
    }

    public PlayerAddTeamPacket buildMemberAddPacket() {
        return TeamViewPacketBuilder.buildMemberAddPacket(this);
    }

    public UpdateTeamPacket buildUpdatePacket() {
        return TeamViewPacketBuilder.buildUpdatePacket(this);
    }


}
