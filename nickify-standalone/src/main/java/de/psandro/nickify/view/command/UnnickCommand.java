package de.psandro.nickify.view.command;

import de.psandro.nickify.controller.NameTagManager;
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

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) return false;
        if (!"unnick".equalsIgnoreCase(command.getName())) return false;
        final Player player = (Player) sender;

            try {
                this.nameTagManager.getNickManager().unnick(player);
                player.sendMessage("Du wurdest entnickt!");
            } catch (Exception e) {
                e.printStackTrace();
                player.sendMessage("Es trat ein Fehler auf: " + e.getMessage());
            }

        return true;
    }
}