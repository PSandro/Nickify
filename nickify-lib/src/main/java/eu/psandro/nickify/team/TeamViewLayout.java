package eu.psandro.nickify.team;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamViewLayout {

    private @NonNull
    String prefix = "";
    private @NonNull
    String suffix = "";
    private @NonNull
    int priority = 0;

    public void applyOn(@NonNull TeamViewLayout layout) {
        layout.setPrefix(this.prefix);
        layout.setSuffix(this.suffix);
        layout.setPriority(this.priority);
    }

}
