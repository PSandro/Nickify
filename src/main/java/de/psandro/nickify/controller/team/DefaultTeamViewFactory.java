package de.psandro.nickify.controller.team;

import com.google.common.base.Preconditions;

import java.util.Random;


public final class DefaultTeamViewFactory implements TeamViewFactory {

    private static final char[] PRIORITYS = {48,49,50,51,52,53,54,55,56,57,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,97,98,99,100,101,102,103,104,105,106,107,108,109,120,121,122};
    private static final Random RANDOM = new Random(System.currentTimeMillis());




    public String buildTeamName(String owner, int priorotyIndex) {
        //TODO check if teamname is available
        final String postName;
        if (owner.length() > 15) {
            postName = owner.substring(0, 15);
        } else if(owner.length() < 15) {
            final int gap = 15 - owner.length();
            final StringBuilder stringBuilder = new StringBuilder();
            for (int i = gap; i >= 1; i--){
                stringBuilder.append(PRIORITYS[RANDOM.nextInt(PRIORITYS.length-1)]);
            }
            postName = owner + stringBuilder.toString();
        } else {
            postName = owner;
        }

        return PRIORITYS[priorotyIndex] + postName;
    }


    @Override
    public TeamView createTeamView(String owner, String prefix, String suffix, int priorityIndex) {
        Preconditions.checkArgument(owner.length() <= 16);
        Preconditions.checkArgument(prefix.length() <= 16);
        Preconditions.checkArgument(suffix.length() <= 16);
        Preconditions.checkArgument(priorityIndex >= 0);
        Preconditions.checkArgument(priorityIndex < 93);

        final String teamName = this.buildTeamName(owner, priorityIndex);

        return new TeamView(owner, teamName, prefix, suffix);
    }

}
