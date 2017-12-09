package de.psandro.nickify.view.inventory.inv;

import de.psandro.nickify.view.inventory.AbstractDynamicInventory;
import de.psandro.nickify.view.inventory.AbstractStaticInventory;
import de.psandro.nickify.view.inventory.InventoryActionCallback;
import de.psandro.nickify.view.inventory.item.ClickableItem;
import de.psandro.nickify.view.inventory.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class MessagesHomeInventory extends AbstractStaticInventory {

    private static final ClickableItem EDIT_MESSAGES_ITEM;
    private static final ClickableItem DOWNLOAD_MESSAGES_ITEM;

    static {
        final ItemStack messagesStack = new ItemBuilder(Material.BOOK)
                .withName("§3Messages")
                .withLore("§7Edit the", "§7Messages")
                .build();
        EDIT_MESSAGES_ITEM = new ClickableItem(messagesStack, (player, clickType, context, inventory) -> {
            inventory.closeSilent(player);
            context.createInventory(player, MessagesViewInventory.class, inventory).open(player);
        });

        final ItemStack settingsStack = new ItemBuilder(Material.TRIPWIRE_HOOK)
                .withName("§3Download Messages")
                .build();
        DOWNLOAD_MESSAGES_ITEM = new ClickableItem(settingsStack, (player, clickType, context, inventory) -> {
            inventory.closeSilent(player);
        });
    }

    public MessagesHomeInventory(AbstractDynamicInventory parent, InventoryActionCallback inventoryActionCallback) {
        super(parent, "§6Nickify Messages Home", 45, inventoryActionCallback);
    }

    @Override
    public void init() {
        super.setItem(21, EDIT_MESSAGES_ITEM);
        super.setItem(23, DOWNLOAD_MESSAGES_ITEM);
        super.setItem(36, super.getParentButtonIfExist());
    }
}
