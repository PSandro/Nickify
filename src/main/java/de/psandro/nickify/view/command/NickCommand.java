package de.psandro.nickify.view.command;

import de.psandro.nickify.controller.NameTagManager;
import de.psandro.nickify.controller.nick.Nickable;
import de.psandro.nickify.controller.team.TeamViewLayout;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

@AllArgsConstructor
public final class NickCommand implements CommandExecutor {

    private static final TeamViewLayout LAYOUT = new TeamViewLayout("§6Genickt ", " Hallo");

    private final @NonNull
    NameTagManager nameTagManager;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) return false;
        if (!"nick".equalsIgnoreCase(command.getName())) return false;
        final Player player = (Player) sender;

        if (args.length <= 0) {
            try {
                this.nameTagManager.getNickManager().nick(player, UUID.fromString("1588abbb-e45b-49e6-9e43-8b83c5d5f812"), LAYOUT);
                player.sendMessage("Du wurdest genickt!");
            } catch (Exception e) {
                e.printStackTrace();
                player.sendMessage("Es trat ein Fehler auf: " + e.getMessage());
            }
        } else if (args.length == 1){
            try {
                Nickable nickable = this.nameTagManager.getNickManager().nick(player, UUID.fromString(args[0]), LAYOUT);
                player.sendMessage("Du wurdest als " + nickable.getNickEntity().getName() +" genickt!");
            } catch (Exception e) {
                e.printStackTrace();
                player.sendMessage("Es trat ein Fehler auf: " + e.getMessage());
            }
        }

        return true;
    }
}
