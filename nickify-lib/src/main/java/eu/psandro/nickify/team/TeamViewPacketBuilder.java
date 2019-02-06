package eu.psandro.nickify.team;

import eu.psandro.nickify.team.wrapper.*;

import java.util.Collections;

public final class TeamViewPacketBuilder {

    public static final CreateTeamPacket buildCreationPacket(final AssignedTeamViewLayout layout) {
        final CreateTeamPacket team = new CreateTeamPacket(layout.getTeamName());
        team.setDisplayName(layout.getTeamName());
        team.setPrefix(layout.getPrefix());
        team.setSuffix(layout.getSuffix());
        team.setPlayers(Collections.singletonList(layout.getOwner()));
        return team;
    }

    public static final RemoveTeamPacket buildRemovePacket(final AssignedTeamViewLayout layout) {
        return new RemoveTeamPacket(layout.getTeamName());
    }

    public static final PlayerRemoveTeamPacket buildMemberRemovePacket(final AssignedTeamViewLayout layout) {
        final PlayerRemoveTeamPacket team = new PlayerRemoveTeamPacket(layout.getTeamName());
        team.setPlayers(Collections.singletonList(layout.getOwner()));
        return team;
    }

    public static final PlayerAddTeamPacket buildMemberAddPacket(final AssignedTeamViewLayout layout) {
        final PlayerAddTeamPacket team = new PlayerAddTeamPacket(layout.getTeamName());
        team.setPlayers(Collections.singletonList(layout.getOwner()));
        return team;
    }

    public static final UpdateTeamPacket buildUpdatePacket(final AssignedTeamViewLayout layout) {
        final UpdateTeamPacket team = new UpdateTeamPacket(layout.getTeamName());
        team.setDisplayName(layout.getTeamName());
        team.setPrefix(layout.getPrefix());
        team.setSuffix(layout.getSuffix());
        return team;
    }


}
