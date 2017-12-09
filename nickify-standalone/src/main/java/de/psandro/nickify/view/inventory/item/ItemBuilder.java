package de.psandro.nickify.view.inventory.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public final class ItemBuilder {

    private final ItemStack itemStack;
    private final ItemMeta meta;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
        this.meta = itemStack.getItemMeta();
    }

    public ItemBuilder withName(String name) {
        this.meta.setDisplayName(name);
        return this;
    }

    public ItemBuilder withLore(String... lore) {
        this.meta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemStack build() {
        this.itemStack.setItemMeta(this.meta);
        return this.itemStack;
    }
}
