package eu.psandro.nickify;

import eu.psandro.nickify.nick.NickProfile;
import eu.psandro.nickify.team.TeamView;
import lombok.Data;
import lombok.NonNull;

/**
 * @author PSandro on 08.02.19
 * @project Nickify
 */
@Data
public class Tag {

    private final @NonNull
    TeamView teamView;
    private final @NonNull
    NickProfile nickProfile;

}
