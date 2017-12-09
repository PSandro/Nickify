package de.psandro.nickify.view.inventory.inv;

import de.psandro.nickify.view.inventory.AbstractStaticInventory;
import de.psandro.nickify.view.inventory.InventoryActionCallback;
import de.psandro.nickify.view.inventory.item.ClickableItem;
import de.psandro.nickify.view.inventory.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class PresetsInventory extends AbstractStaticInventory {

    private static final ClickableItem TEAM_VIEW_ITEM;
    private static final ClickableItem NICK_ITEM;

    static {
        final ItemStack messagesStack = new ItemBuilder(Material.IRON_CHESTPLATE)
                .withName("§3TeamView Presets")
                .withLore("§7Edit the", "§7TeamView Presets")
                .build();
        TEAM_VIEW_ITEM = new ClickableItem(messagesStack, (player, clickType, context) -> {
            //TODO open inventory
        });

        final ItemStack settingsStack = new ItemBuilder(Material.NAME_TAG)
                .withName("§3Nick Presets")
                .withLore("§7Edit the", "§7Nick Presets")
                .build();
        NICK_ITEM = new ClickableItem(settingsStack, (player, clickType, context) -> {
            //TODO open inventory
        });
    }

    public PresetsInventory(InventoryActionCallback inventoryActionCallback) {
        super("§6Nickify Presets", 45, inventoryActionCallback);
    }

    @Override
    public void init() {
        super.setItem(21, TEAM_VIEW_ITEM);
        super.setItem(23, NICK_ITEM);
    }
}
