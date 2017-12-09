package de.psandro.nickify.view.inventory;

import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public abstract class AbstractStaticInventory extends AbstractDynamicInventory {

    protected AbstractStaticInventory(String title, int size, InventoryActionCallback inventoryActionCallback) {
        super(null, title, size, inventoryActionCallback);
        this.init();
    }

    public abstract void init();

    @Override
    public void open(Player player) {
        super.open(player);
    }

    @Override
    public void close(Player player) {
        super.close(player);
    }
}
