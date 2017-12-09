package de.psandro.nickify.view.inventory;

import com.google.common.collect.*;
import de.psandro.nickify.view.inventory.inv.HomeInventory;
import de.psandro.nickify.view.inventory.inv.PresetsInventory;
import de.psandro.nickify.view.inventory.item.ClickableItem;
import de.psandro.nickify.view.inventory.item.DumpItem;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.*;

public final class InventoryFactory implements Listener {

    private final Map<Class<? extends AbstractStaticInventory>, AbstractStaticInventory> staticInventories = new HashMap<>();
    private final Table<UUID, String, AbstractDynamicInventory> inventoryBindings = HashBasedTable.create();
    private final ActionListener actionListener;


    public InventoryFactory() {
        this.actionListener = new ActionListener(this.inventoryBindings);

        this.staticInventories.put(HomeInventory.class, new HomeInventory(this.actionListener));
        this.staticInventories.put(PresetsInventory.class, new PresetsInventory(this.actionListener));

    }

    public AbstractDynamicInventory createInventory(Player player, Class<? extends AbstractDynamicInventory> inventory) {
        //add to bindings
        return null; //TODO
    }

    public AbstractStaticInventory getStaticInventory(Class<? extends AbstractStaticInventory> inventory) {
        return this.staticInventories.get(inventory);
    }

    public AbstractDynamicInventory getInventory(UUID uuid, String title) {
        if (!this.inventoryBindings.contains(uuid, title)) return null;
        return this.inventoryBindings.get(uuid, title);
    }

    @EventHandler
    public void onClick(final InventoryClickEvent event) {
        event.setCancelled(false);
        if (event.getClickedInventory() == null) return;
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
        if (event.getWhoClicked() == null) return;
        final Player player = (Player) event.getWhoClicked();
        final AbstractDynamicInventory inventory = this.getInventory(player.getUniqueId(), event.getClickedInventory().getTitle());
        if (inventory == null) return;
        event.setCancelled(true);
        final DumpItem dumpItem = inventory.getContents()[event.getSlot()];
        if (dumpItem == null) return;
        if (dumpItem instanceof ClickableItem) {
            ((ClickableItem) dumpItem).getClickCallback().click(player, event.getClick(), this);
        }
    }

    @AllArgsConstructor
    private class ActionListener implements InventoryActionCallback {

        private final Table<UUID, String, AbstractDynamicInventory> bindings;

        @Override
        public void close(Player player, AbstractDynamicInventory inventory) {
            if (bindings.contains(player.getUniqueId(), inventory.getTitle())) {
                bindings.remove(player.getUniqueId(), inventory.getTitle());
            }
        }

        @Override
        public void open(Player player, AbstractDynamicInventory inventory) {
            this.bindings.put(player.getUniqueId(), inventory.getTitle(), inventory);
        }
    }

}
