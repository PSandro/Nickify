package de.psandro.nickify.view.command;

import com.google.common.collect.ImmutableMap;
import de.psandro.nickify.controller.NameTagManager;
import de.psandro.nickify.controller.Permissions;
import de.psandro.nickify.controller.message.IMessageManager;
import de.psandro.nickify.controller.message.MessageId;
import de.psandro.nickify.controller.nick.Nickable;
import de.psandro.nickify.misc.TeamViewFetcher;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

@AllArgsConstructor
public final class NickCommand implements CommandExecutor {


    private final @NonNull
    NameTagManager nameTagManager;

    private final @NonNull
    IMessageManager messageManager;

    private final @NonNull
    TeamViewFetcher teamViewFetcher;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) return false;
        if (!"nick".equalsIgnoreCase(command.getName())) return false;
        final Player player = (Player) sender;

        if (!player.hasPermission(Permissions.NICK_USE)) {
            this.messageManager.sendIfPresent(player, MessageId.NO_PERMISSION, ImmutableMap.of(MessageId.MessageSpacer.COMMAND_SPACER, command.getName()));
            return false;
        }

        if (args.length <= 0) {
            try {
                final Nickable nickable = this.nameTagManager.getNickManager().nick(player, UUID.fromString("1588abbb-e45b-49e6-9e43-8b83c5d5f812"), teamViewFetcher.getRandomNickLayout());
                this.messageManager.sendIfPresent(player, MessageId.GET_NICK, ImmutableMap.of(MessageId.MessageSpacer.NICKNAME_SPACER, nickable.getNickEntity().getName()));
            } catch (Exception e) {
                e.printStackTrace();
                this.messageManager.sendIfPresent(player, MessageId.UNKNOWN_ERROR, ImmutableMap.of(MessageId.MessageSpacer.ERROR, e.getMessage()));
            }
        } else if (args.length == 1) {
            try {
                Nickable nickable = this.nameTagManager.getNickManager().nick(player, UUID.fromString(args[0]), teamViewFetcher.getRandomNickLayout());
                this.messageManager.sendIfPresent(player, MessageId.GET_NICK, ImmutableMap.of(MessageId.MessageSpacer.NICKNAME_SPACER, nickable.getNickEntity().getName()));
            } catch (Exception e) {
                e.printStackTrace();
                this.messageManager.sendIfPresent(player, MessageId.UNKNOWN_ERROR, ImmutableMap.of(MessageId.MessageSpacer.ERROR, e.getMessage()));
            }
        }

        return true;
    }
}
