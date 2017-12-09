package de.psandro.nickify.view.inventory.item;

import de.psandro.nickify.view.inventory.AbstractDynamicInventory;
import lombok.*;
import org.bukkit.inventory.ItemStack;

@EqualsAndHashCode
public class ClickableItem<E extends AbstractDynamicInventory> extends DumpItem {

    @Getter
    private @NonNull
    ItemClickCallback<E> clickCallback;

    public ClickableItem(ItemStack itemStack, final ItemClickCallback<E> clickCallback) {
        super(itemStack);
        this.clickCallback = clickCallback;
    }

    public void onClick(@NonNull ItemClickCallback<E> clickCallback) {
        this.clickCallback = clickCallback;
    }
}
