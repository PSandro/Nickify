package de.psandro.nickify.view.inventory.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public final class ItemBuilder {

    private final ItemStack itemStack;
    private final ItemMeta meta;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
        this.meta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, short meta) {
        this.itemStack = new ItemStack(material,1,  meta);
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

    public static ItemStack buildHead(String name, String itemName, String... lore) {
        final ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        final SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setLore(Arrays.asList(lore));
        skullMeta.setDisplayName(itemName);
        skullMeta.setOwner(name);
        itemStack.setItemMeta(skullMeta);
        return itemStack;
    }

}
