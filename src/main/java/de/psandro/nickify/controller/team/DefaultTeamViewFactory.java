package de.psandro.nickify.controller.team;

import com.google.common.base.Preconditions;
import de.psandro.nickify.controller.exception.NoTeamNameAvailableException;
import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


public final class DefaultTeamViewFactory implements TeamViewFactory {

    private static final char[] PRIORITYS = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 120, 121, 122};
    private static final Random RANDOM = new Random(System.currentTimeMillis());

    private static final AtomicInteger count = new AtomicInteger();


    public String buildTeamName(String owner, int priorotyIndex) {
        final String postName;
        if (owner.length() > 3) {
            postName = owner.substring(0, 3);
        } else {
            final int gap = 3 - owner.length();
            final StringBuilder stringBuilder = new StringBuilder();
            for (int i = gap; i >= 1; i--) {
                stringBuilder.append(PRIORITYS[RANDOM.nextInt(PRIORITYS.length - 1)]);
            }
            postName = owner + stringBuilder.toString();
        }

        final String nameSuffix = String.valueOf(this.intToCharArray(count.getAndIncrement()));

        if (nameSuffix.length() > 12) throw new NoTeamNameAvailableException();

        return PRIORITYS[priorotyIndex] + postName + nameSuffix;
    }

    private final char[] intToCharArray(final int value) {
        List<Integer> snippets = new ArrayList<>();
        int num = value;
        int index = PRIORITYS.length - 1;
        if (num < index) return new char[]{PRIORITYS[num]};
        for (; num != 0; num /= index)
            snippets.add(num % index);
        char[] arr = new char[snippets.size()];
        for (int i = 0; i < snippets.size(); i++) {
            arr[i] = PRIORITYS[i];
        }
        ArrayUtils.reverse(arr);

        return arr;
    }


    @Override
    public TeamView createTeamView(String owner, TeamViewLayout teamViewLayout, int priorityIndex) {
        Preconditions.checkArgument(owner.length() <= 16);
        Preconditions.checkArgument(teamViewLayout.getPrefix().length() <= 16);
        Preconditions.checkArgument(teamViewLayout.getSuffix().length() <= 16);
        Preconditions.checkArgument(priorityIndex >= 0);
        Preconditions.checkArgument(priorityIndex < 93);

        final String teamName = this.buildTeamName(owner, priorityIndex);

        return new TeamView(teamViewLayout, owner, teamName);
    }

    @Override
    public void resetCounter() {
        count.set(0);
    }

}
