package de.psandro.nickify.controller.team;

import com.google.common.base.Preconditions;
import de.psandro.nickify.controller.team.wrapper.NameTagVisibility;

import java.util.Random;


public final class DefaultTeamViewFactory implements TeamViewFactory {

    private static final char[] PRIORITYS = new char[94];
    private static final Random RANDOM = new Random(System.currentTimeMillis());

    static {
        for (char c = 33; c < 126; c++) {
            PRIORITYS[c-33] = c;
        }
    }


    public String buildTeamName(String owner, int priorotyIndex) {
        //TODO check if teamname is available
        final String postName;
        if (owner.length() > 15) {
            postName = owner.substring(0, 15);
        } else if(owner.length() < 15) {
            final int gap = 15 - owner.length();
            final StringBuilder stringBuilder = new StringBuilder();
            for (int i = gap; i > 1; i--){
                stringBuilder.append(PRIORITYS[RANDOM.nextInt(93)]);
            }
            postName = owner + stringBuilder.toString();
        } else {
            postName = owner;
        }

        return PRIORITYS[priorotyIndex] + postName;
    }


    @Override
    public TeamView createTeamView(String owner, String prefix, String suffix, int priorityIndex, NameTagVisibility nameTagVisibility) {
        Preconditions.checkArgument(owner.length() <= 16);
        Preconditions.checkArgument(prefix.length() <= 16);
        Preconditions.checkArgument(suffix.length() <= 16);
        Preconditions.checkArgument(priorityIndex >= 0);
        Preconditions.checkArgument(priorityIndex < 93);

        final String teamName = this.buildTeamName(owner, priorityIndex);

        return new TeamView(owner, teamName, prefix, suffix, nameTagVisibility);
    }

    @Override
    public TeamView createTeamView(String owner, String prefix, String suffix, int priority) {
        return this.createTeamView(owner, prefix, suffix, priority, NameTagVisibility.ALWAYS);
    }
}
