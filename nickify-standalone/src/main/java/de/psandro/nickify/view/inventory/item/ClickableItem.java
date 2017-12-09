package de.psandro.nickify.view.inventory.item;

import lombok.*;
import org.bukkit.inventory.ItemStack;

@EqualsAndHashCode
public class ClickableItem extends DumpItem {

    @Getter
    private @NonNull
    ItemClickCallback clickCallback;

    public ClickableItem(ItemStack itemStack, final ItemClickCallback clickCallback) {
        super(itemStack);
        this.clickCallback = clickCallback;
    }

    public void onClick(@NonNull ItemClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }
}
