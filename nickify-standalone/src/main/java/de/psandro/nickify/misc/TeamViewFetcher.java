package de.psandro.nickify.misc;

import de.psandro.nickify.controller.team.TeamView;
import de.psandro.nickify.controller.team.TeamViewFactory;
import de.psandro.nickify.controller.team.TeamViewLayout;
import de.psandro.nickify.model.TeamViewPreset;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.Random;
import java.util.Set;

@AllArgsConstructor
public final class TeamViewFetcher {

    private static final Comparator<TeamViewLayout> LAYOUT_COMPARATOR = Comparator.comparingInt(TeamViewLayout::getPriority);
    private static final Random RANDOM = new Random(System.currentTimeMillis());

    private final @NonNull
    Set<TeamViewPreset> presets;
    private final @NonNull
    TeamViewLayout defaultTeamViewPreset;
    private final TeamViewLayout[] nickPresets;

    public TeamView getTeamView(final Player player) {
        final TeamViewLayout preset = this.getPreset(player);
        return TeamViewFactory.createTeamView(player.getName(), preset);
    }

    public TeamView getNickTeamView(final String name) {
        final TeamViewLayout preset = this.getRandomNickLayout();
        return TeamViewFactory.createTeamView(name, preset);
    }

    public TeamViewLayout getPreset(final Player player) {
        return this.presets.stream().filter(preset -> player.hasPermission(preset.getPermission())).map(TeamViewPreset::getLayout).sorted(LAYOUT_COMPARATOR).findFirst().orElse(this
                .defaultTeamViewPreset);
    }

    public TeamViewLayout getRandomNickLayout() {
        if (this.nickPresets == null || this.nickPresets.length <= 0) return this.defaultTeamViewPreset;
        return nickPresets[RANDOM.nextInt(nickPresets.length)];
    }

}
