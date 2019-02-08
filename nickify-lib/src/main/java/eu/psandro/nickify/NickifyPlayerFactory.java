package eu.psandro.nickify;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import eu.psandro.nickify.exception.NickifyException;
import eu.psandro.nickify.nick.NickProcessor;
import eu.psandro.nickify.team.TeamProcessor;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

/**
 * @author PSandro on 08.02.19
 * @project Nickify
 */
public class NickifyPlayerFactory {

    private static final Set<NickifyPlayer> players = Sets.newHashSet();
    private static final Table<UUID, UUID, Tag> customTag = Tables.synchronizedTable(HashBasedTable.create()); // observer - target - target tag

    public static NickifyPlayer createPlayer(final Player player) {
        if (players.parallelStream()
                .anyMatch(pl -> pl.getUniqueId().equals(player.getUniqueId()))) {
            throw new NickifyException(
                    String.format("Player with UUID %s has already been created.", player.getUniqueId().toString())
            );
        }

        final NickProcessor nickProcessor = null; //TODO: init nickProcessor
        final TeamProcessor teamProcessor = null; //TODO: init teamProcessor

        final Tag tag = null; //TODO: init tag

        final NickifyPlayer nickifyPlayer = new NickifyPlayer(player, nickProcessor, teamProcessor, tag);

        players.add(nickifyPlayer);
        return nickifyPlayer;
    }

}
