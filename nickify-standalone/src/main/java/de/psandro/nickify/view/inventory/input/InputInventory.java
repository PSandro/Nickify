package de.psandro.nickify.view.inventory.input;

import de.psandro.nickify.view.inventory.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;


public class InputInventory {

    private static final WrapperPlayServerCustomPayload OPEN_BOOK_PACKET = new WrapperPlayServerCustomPayload();

    static {
        OPEN_BOOK_PACKET.setChannel("MC|BOpen");
    }


    private final ItemStack itemStack;
    private final InventoryInput handler;

    public InputInventory(String preset, InventoryInput handler) {
        this.itemStack = new ItemBuilder(Material.BOOK_AND_QUILL).build();
        this.handler = handler;
        this.setText(preset);
    }

    private void setText(String text) {
        final BookMeta bookMeta = (BookMeta) this.itemStack.getItemMeta();
        bookMeta.setPages(text);
        this.itemStack.setItemMeta(bookMeta);
    }

    public void open(Player player) {
        final ItemStack current = player.getItemInHand();
        player.setItemInHand(this.itemStack);
        try {
            OPEN_BOOK_PACKET.sendPacket(player);
        } finally {
            player.setItemInHand(current);
        }


    }


    @FunctionalInterface
    public interface InventoryInput {

        void onInput(String text);

    }

}
