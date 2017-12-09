package de.psandro.nickify.view.command;

import de.psandro.nickify.controller.message.IMessageManager;
import de.psandro.nickify.view.inventory.InventoryFactory;
import de.psandro.nickify.view.inventory.inv.HomeInventory;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@AllArgsConstructor
public final class NickifyCommand implements CommandExecutor {


    private final @NonNull
    IMessageManager messageManager;
    private final @NonNull
    InventoryFactory inventoryFactory;


    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) return false;
        if (!"nickify".equalsIgnoreCase(command.getName())) return false;
        final Player player = (Player) sender;

        if (args.length == 0) {
            this.inventoryFactory.getStaticInventory(HomeInventory.class).open(player);
        }
        return true;
    }
}
