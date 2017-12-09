package de.psandro.nickify.view.inventory.item;

import de.psandro.nickify.view.inventory.InventoryFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public interface ItemClickCallback {

    void click(Player player, ClickType clickType, InventoryFactory context);

}
