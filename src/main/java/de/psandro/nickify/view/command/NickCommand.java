package de.psandro.nickify.view.command;

import de.psandro.nickify.controller.NameTagManager;
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

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) return false;
        if (!"nick".equalsIgnoreCase(command.getName())) return false;
        final Player player = (Player) sender;

        if (args.length <= 0) {
            try {
                this.nameTagManager.getNickManager().nick(player, UUID.fromString("1588abbb-e45b-49e6-9e43-8b83c5d5f812"));
                player.sendMessage("Du wurdest genickt!");
            } catch (Exception e) {
                e.printStackTrace();
                player.sendMessage("Es trat ein Fehler auf: " + e.getMessage());
            }
        } else if (args.length == 1){
            try {
                this.nameTagManager.getNickManager().nick(player, args[0]);
                player.sendMessage("Du wurdest als " + args[0] +" genickt!");
            } catch (Exception e) {
                e.printStackTrace();
                player.sendMessage("Es trat ein Fehler auf: " + e.getMessage());
            }
        }

        return true;
    }
}
