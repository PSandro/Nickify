package de.psandro.nickify.view.inventory;

import com.google.common.base.Preconditions;
import de.psandro.nickify.view.inventory.item.DumpItem;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;


public abstract class AbstractDynamicInventory {

    @Getter
    private final Player holder;
    private final Inventory inventory;
    @Getter
    private final DumpItem[] contents;
    @Getter
    private final InventoryActionCallback inventoryActionCallback;

    protected AbstractDynamicInventory(final Player holder, String title, int size, InventoryActionCallback inventoryActionCallback) {
        Preconditions.checkArgument(size % 9 == 0);
        Preconditions.checkArgument(size <= 54);
        Preconditions.checkArgument(size >= 9);
        this.inventory = Bukkit.createInventory(holder, size, title);
        this.contents = new DumpItem[size];
        this.holder = holder;
        this.inventoryActionCallback = inventoryActionCallback;
    }


    public AbstractDynamicInventory setItem(int index, @NonNull DumpItem dumpItem) {
        this.contents[index] = dumpItem;
        this.inventory.setItem(index, dumpItem.getItemStack());
        return this;
    }

    protected void open(final Player player) {
        this.inventoryActionCallback.open(player, this);
        player.openInventory(this.inventory);
    }

    protected void close(final Player player) {
        this.inventoryActionCallback.close(player, this);
    }

    public final void open() {
        this.open(this.holder);
    }

    public final void close() {
        this.close(this.holder);
    }

    public String getTitle() {
        return this.inventory.getTitle();
    }


}
