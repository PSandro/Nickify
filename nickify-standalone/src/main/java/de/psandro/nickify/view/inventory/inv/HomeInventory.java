package de.psandro.nickify.view.inventory.inv;

import de.psandro.nickify.view.inventory.AbstractStaticInventory;
import de.psandro.nickify.view.inventory.InventoryActionCallback;
import de.psandro.nickify.view.inventory.item.ClickableItem;
import de.psandro.nickify.view.inventory.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class HomeInventory extends AbstractStaticInventory {

    private static final ClickableItem MESSAGES_ITEM;
    private static final ClickableItem SETTINGS_ITEM;

    static {
        final ItemStack messagesStack = new ItemBuilder(Material.PAPER)
                .withName("§3Messages")
                .withLore("§7Edit the", "§7Messages here")
                .build();
        MESSAGES_ITEM = new ClickableItem(messagesStack, (player, clickType, context, inventory) -> {
            inventory.closeSilent(player);
            context.getStaticInventory(MessagesHomeInventory.class).open(player);
        });

        final ItemStack settingsStack = new ItemBuilder(Material.WORKBENCH)
                .withName("§3Settings")
                .withLore("§7Edit the", "§7Settings here")
                .build();
        SETTINGS_ITEM = new ClickableItem(settingsStack, (player, clickType, context, inventory) -> {
            inventory.closeSilent(player);
            context.getStaticInventory(PresetsInventory.class).open(player);
        });
    }

    public HomeInventory(InventoryActionCallback inventoryActionCallback) {
        super(null,"§6Nickify Home", 45, inventoryActionCallback);
    }

    @Override
    public void init() {
        super.setItem(21, MESSAGES_ITEM);
        super.setItem(23, SETTINGS_ITEM);
    }
}
