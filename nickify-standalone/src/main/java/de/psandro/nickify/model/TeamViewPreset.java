package de.psandro.nickify.model;

import de.psandro.nickify.api.team.TeamViewLayout;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public final class TeamViewPreset {
    private @NonNull
    String name;
    private @NonNull
    TeamViewLayout layout;
    private @NonNull
    String permission;


}
