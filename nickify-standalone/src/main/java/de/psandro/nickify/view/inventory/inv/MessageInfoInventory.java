package de.psandro.nickify.view.inventory.inv;

import de.psandro.nickify.api.model.MessageFormat;
import de.psandro.nickify.api.model.UnsafeMessage;
import de.psandro.nickify.model.ConfigManager;
import de.psandro.nickify.view.inventory.AbstractDynamicInventory;
import de.psandro.nickify.view.inventory.InventoryActionCallback;
import de.psandro.nickify.view.inventory.input.InputInventory;
import de.psandro.nickify.view.inventory.item.ClickableItem;
import de.psandro.nickify.view.inventory.item.DumpItem;
import de.psandro.nickify.view.inventory.item.ItemBuilder;
import de.psandro.nickify.view.inventory.item.ItemClickCallback;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MessageInfoInventory extends AbstractDynamicInventory {


    private static final ClickableItem<MessageInfoInventory> ACTIVATE_ITEM;
    private static final ClickableItem<MessageInfoInventory> DEACTIVATE_ITEM;
    private static final ClickableItem<MessageInfoInventory> CREATE_ITEM;
    private static final ClickableItem<MessageInfoInventory> CHANGE_ITEM;
    private static final ClickableItem<MessageInfoInventory> SAVE_ITEM;

    private static final int ACTIVITY_BUTTON_SLOT = 21;
    private static final int CREATION_BUTTON_SLOT = 22;
    private static final int CHANGE_BUTTON_SLOT = 23;
    private static final int SAVE_BUTTON_SLOT = 40;


    static {
        final ItemStack activateStack = new ItemBuilder(Material.STAINED_CLAY, (short) 5)
                .withName("§aactivate")
                .withLore("§7Click here to activate", "§7this message")
                .build();
        final ItemStack createStack = new ItemBuilder(Material.STAINED_CLAY, (short) 5)
                .withName("§acreate")
                .withLore("§7Click here to create", "§7this message")
                .build();
        final ItemStack deactivateStack = new ItemBuilder(Material.EYE_OF_ENDER)
                .withName("§cdeactivate")
                .withLore("§7Click here to deactivate", "§7this message")
                .build();
        final ItemStack changeStack = new ItemBuilder(Material.SIGN)
                .withName("§cchange")
                .withLore("§7Click here to change", "§7this message")
                .build();
        final ItemStack saveStack = new ItemBuilder(Material.NETHER_STAR)
                .withName("§asave")
                .withLore("§7Click here to save", "§7all changes")
                .build();


        final ItemClickCallback<MessageInfoInventory> clickCallback = (player, clickType, context, inventory) -> {
            inventory.invertActivity();
            inventory.addSaveButton();
        };


        ACTIVATE_ITEM = new ClickableItem<>(activateStack, clickCallback);
        DEACTIVATE_ITEM = new ClickableItem<>(deactivateStack, clickCallback);
        CHANGE_ITEM = new ClickableItem<>(changeStack, (player, clickType, context, inventory) -> {

            if (inventory.isMessageFormat()) {
                player.closeInventory();
                new InputInventory(((MessageFormat) inventory.getMessage()).buildMessage(), null).open(player);
                inventory.addSaveButton();
            }


        });
        CREATE_ITEM = new ClickableItem<>(createStack, (player, clickType, context, inventory) -> {
            if (!inventory.isMessageFormat()) {
                inventory.message = new MessageFormat("", inventory.message.getMessageId(), false);
                inventory.setItem(CREATION_BUTTON_SLOT, null);
                inventory.setItem(CHANGE_BUTTON_SLOT, CHANGE_ITEM);
                inventory.setItem(ACTIVITY_BUTTON_SLOT, ACTIVATE_ITEM);
                inventory.addSaveButton();
            }
        });
        SAVE_ITEM = new ClickableItem<>(saveStack, (player, clickType, context, inventory) -> {
            if (inventory.isMessageFormat()) {
                inventory.configManager.getMessageEntity().setMessage((MessageFormat) inventory.message);
                inventory.render();
                ((MessagesViewInventory) inventory.getParent()).refresh();
            }
        });


    }

    @Getter
    private UnsafeMessage message;
    private final ConfigManager configManager;

    public MessageInfoInventory(AbstractDynamicInventory parent, Player holder, InventoryActionCallback inventoryActionCallback, UnsafeMessage message, ConfigManager configManager) {
        super(parent, holder, "§6Nickify Message Info", 45, inventoryActionCallback);
        this.message = message;
        this.configManager = configManager;
        this.render();
    }

    private boolean isMessageFormat() {
        return this.message instanceof MessageFormat;
    }

    private void invertActivity() {
        final boolean state = !this.isActive();
        ((MessageFormat) this.message).setActive(state);
        this.setItem(21, this.getActivityButton());
    }

    private boolean isActive() {
        return ((MessageFormat) this.message).isActive();
    }

    private DumpItem getActivityButton() {
        return this.isActive() ? DEACTIVATE_ITEM : ACTIVATE_ITEM;
    }

    private void addSaveButton() {
        this.setItem(SAVE_BUTTON_SLOT, SAVE_ITEM);
    }


    private void render() {
        final DumpItem message = new DumpItem(MessagesViewInventory.messageToItemStack(this.message));
        super.clearInventory();
        super.setItem(4, message);
        if (this.isMessageFormat()) {
            super.setItem(ACTIVITY_BUTTON_SLOT, this.getActivityButton());
            super.setItem(CHANGE_BUTTON_SLOT, CHANGE_ITEM);
        } else {
            super.setItem(CREATION_BUTTON_SLOT, CREATE_ITEM);
        }

        super.setItem(36, PARENT_INV_ITEM);
    }


}
