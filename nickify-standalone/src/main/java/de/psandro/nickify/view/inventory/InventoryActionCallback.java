package de.psandro.nickify.view.inventory;

import org.bukkit.entity.Player;

public interface InventoryActionCallback {

    void close(Player player, AbstractDynamicInventory inventory);

    void open(Player player, AbstractDynamicInventory inventory);

}
