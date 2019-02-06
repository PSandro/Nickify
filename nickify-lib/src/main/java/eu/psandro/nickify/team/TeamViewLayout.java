package eu.psandro.nickify.team;

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
    private @NonNull
    int priority;

    public void executeOn(@NonNull TeamViewLayout layout) {
        layout.setPrefix(this.prefix);
        layout.setSuffix(this.suffix);
        layout.setPriority(this.priority);
    }

}
