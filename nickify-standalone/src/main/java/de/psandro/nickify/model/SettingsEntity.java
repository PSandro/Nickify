package de.psandro.nickify.model;

import de.psandro.nickify.controller.exception.ConfigurationException;
import de.psandro.nickify.controller.team.TeamViewLayout;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public final class SettingsEntity {

    private Set<TeamViewPreset> teamViewPresets;
    private Set<String> nickTeamViewPresets;

    public TeamViewLayout getDefaultPreset() {
        return teamViewPresets.stream().filter(preset -> preset.getName().equals("default")).findAny().orElseThrow(() -> new ConfigurationException("No default TeamViewPreset found!")).getLayout();
    }

    public TeamViewLayout[] getNickPresets() {
        return this.teamViewPresets.stream().filter(name -> this.nickTeamViewPresets.contains(name)).map(TeamViewPreset::getLayout).toArray(TeamViewLayout[]::new);
    }

}
