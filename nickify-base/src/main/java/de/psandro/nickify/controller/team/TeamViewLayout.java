package de.psandro.nickify.controller.team;

import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class TeamViewLayout {

    private @NonNull
    String prefix;
    private @NonNull
    String suffix;

    public void executeOn(@NonNull TeamViewLayout layout) {
        layout.setPrefix(this.prefix);
        layout.setSuffix(this.suffix);
    }

}
