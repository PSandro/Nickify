package de.psandro.nickify.view.inventory;

import com.google.common.base.Preconditions;
import de.psandro.nickify.view.inventory.item.ClickableItem;
import de.psandro.nickify.view.inventory.item.DumpItem;
import de.psandro.nickify.view.inventory.item.ItemBuilder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;


public abstract class AbstractDynamicInventory<E extends AbstractDynamicInventory> {

    public static final ClickableItem PARENT_INV_ITEM;
    public static final ItemStack PARENT_INV_STACK = ItemBuilder.buildHead("MHF_ArrowLeft", "§6parent inventory", "", "§7Go back to the", "§7parent inventory");


    static {

        PARENT_INV_ITEM = new ClickableItem<>(PARENT_INV_STACK, (player, clickType, context, inventory) -> {
            inventory.closeSilent(player);
            inventory.parent.open(player);
        });

    }

    @Getter
    private final Player holder;
    private final Inventory inventory;
    @Getter
    private final DumpItem[] contents;
    @Getter
    private final InventoryActionCallback inventoryActionCallback;
    @Getter
    private final AbstractDynamicInventory<E> parent;

    protected AbstractDynamicInventory(final AbstractDynamicInventory<E> parent, final Player holder, String title, int size, InventoryActionCallback inventoryActionCallback) {
        Preconditions.checkArgument(size % 9 == 0);
        Preconditions.checkArgument(size <= 54);
        Preconditions.checkArgument(size >= 9);
        this.inventory = Bukkit.createInventory(holder, size, title);
        this.contents = new DumpItem[size];
        this.holder = holder;
        this.inventoryActionCallback = inventoryActionCallback;
        this.parent = parent;
    }


    public AbstractDynamicInventory setItem(int index, DumpItem dumpItem) {
        this.contents[index] = dumpItem;
        //System.out.println("setting item " + index + " : " + (dumpItem == null ? "null" : dumpItem.getItemStack().getItemMeta().getDisplayName()));
        this.inventory.setItem(index, dumpItem == null ? null : dumpItem.getItemStack());
        return this;
    }

    public void setContents(DumpItem[] items) {
        if (items.length == this.contents.length) {
            for (int i = 0; i < items.length; i++) {
                final DumpItem item = items[i];
                this.setItem(i, item);
            }
        }
    }

    public void open(final Player player) {
        this.inventoryActionCallback.open(player, this);
        player.openInventory(this.inventory);
    }

    public void close(final Player player) {
        this.inventoryActionCallback.close(player, this);
        player.closeInventory();
    }


    public void clearInventory() {
        this.inventory.clear();
        Arrays.fill(this.contents, null);
    }

    public void closeSilent(final Player player) {
        this.inventoryActionCallback.close(player, this);
    }

    public String getTitle() {
        return this.inventory.getTitle();
    }


    public DumpItem getParentButtonIfExist() {
        if (this.parent != null) return PARENT_INV_ITEM;
        return null;
    }
}
