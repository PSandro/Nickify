package de.psandro.nickify.view.inventory;

import com.google.common.collect.*;
import de.psandro.nickify.controller.message.MessageId;
import de.psandro.nickify.controller.message.UnsafeMessage;
import de.psandro.nickify.model.ConfigManager;
import de.psandro.nickify.view.inventory.inv.*;
import de.psandro.nickify.view.inventory.item.ClickableItem;
import de.psandro.nickify.view.inventory.item.DumpItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.*;
import java.util.stream.Collectors;

public final class InventoryFactory implements Listener {

    private final Map<Class<? extends AbstractStaticInventory>, AbstractStaticInventory> staticInventories = new HashMap<>();
    private final Table<UUID, String, AbstractDynamicInventory> inventoryBindings = HashBasedTable.create();
    @Getter
    private final ActionListener actionListener;
    @Getter
    private final ConfigManager configManager;


    public InventoryFactory(ConfigManager configManager) {
        this.configManager = configManager;
        this.actionListener = new ActionListener(this.inventoryBindings);

        final AbstractStaticInventory homeInventory = new HomeInventory(this.actionListener);
        this.staticInventories.put(HomeInventory.class, homeInventory);
        this.staticInventories.put(MessagesHomeInventory.class, new MessagesHomeInventory(homeInventory, this.actionListener));
        this.staticInventories.put(PresetsInventory.class, new PresetsInventory(homeInventory, this.actionListener));

    }

    public AbstractDynamicInventory createInventory(Player player, Class<? extends AbstractDynamicInventory> inventory, AbstractDynamicInventory parent) {
        if (inventory.equals(MessagesViewInventory.class)) {


            return new MessagesViewInventory(parent, player, this.actionListener, this.configManager);
        } else if (inventory.equals(TeamViewPresetsInventory.class)) {
            return new TeamViewPresetsInventory(parent, player, this.actionListener, this.configManager.getSettingsEntity().getTeamViewPresets());
        }
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
            ((ClickableItem) dumpItem).getClickCallback().click(player, event.getClick(), this, inventory);
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
