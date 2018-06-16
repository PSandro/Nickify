package de.psandro.nickify.view.command;

import com.google.common.collect.ImmutableMap;
import de.psandro.nickify.controller.NameTagManager;
import de.psandro.nickify.controller.message.IMessageManager;
import de.psandro.nickify.api.model.MessageId;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@AllArgsConstructor
public final class UnnickCommand implements CommandExecutor {

    private final @NonNull
    NameTagManager nameTagManager;
    private final @NonNull
    IMessageManager messageManager;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) return false;
        if (!"unnick".equalsIgnoreCase(command.getName())) return false;
        final Player player = (Player) sender;

        try {
            this.nameTagManager.getNickManager().unnick(player);
            this.messageManager.sendIfPresent(player, MessageId.UNNICK, ImmutableMap.of());
        } catch (Exception e) {
            e.printStackTrace();
            this.messageManager.sendIfPresent(player, MessageId.UNKNOWN_ERROR, ImmutableMap.of(MessageId.MessageSpacer.ERROR, e.getMessage()));
        }

        return true;
    }
}