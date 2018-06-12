package de.psandro.nickify.view.inventory;

import de.psandro.nickify.view.inventory.item.ClickableItem;
import de.psandro.nickify.view.inventory.item.DumpItem;
import de.psandro.nickify.view.inventory.item.ItemBuilder;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class AbstractMultisiteInventory extends AbstractDynamicInventory {

    public static final ClickableItem PREVIOUS_SITE_ITEM;
    public static final ClickableItem NEXT_SITE_ITEM;

    static {
        final ItemStack siteBackStack = ItemBuilder.buildHead("MHF_ArrowLeft", "§6previous site", "", "§7Go back to the", "§7previous site");
        final ItemStack siteNexStack = ItemBuilder.buildHead("MHF_ArrowRight", "§6next site", "", "§7Go to the", "§7next site");

        PREVIOUS_SITE_ITEM = new ClickableItem<>(siteBackStack, (player, clickType, context, inventory) -> {
            if (inventory instanceof AbstractMultisiteInventory) {
                ((AbstractMultisiteInventory) inventory).openPage(PageAction.DECREMENT);
            }
        });

        NEXT_SITE_ITEM = new ClickableItem<>(siteNexStack, (player, clickType, context, inventory) -> {
            if (inventory instanceof AbstractMultisiteInventory) {
                ((AbstractMultisiteInventory) inventory).openPage(PageAction.DECREMENT);
            }
        });
    }

    private static final int PAGE_SIZE = 45;
    private int currentPage = 0;
    private @NonNull
    List<DumpItem> dumpItems;
    private int maxPage;


    public AbstractMultisiteInventory(AbstractDynamicInventory parent, Player holder, String title, InventoryActionCallback inventoryActionCallback, List<DumpItem> dumpItems) {
        super(parent, holder, title, 54, inventoryActionCallback);
        this.dumpItems = dumpItems;
        this.maxPage = this.calcMaxPage(dumpItems.size());
        this.openPage(PageAction.START);
    }

    public void refresh(List<DumpItem> dumpItems) {
        this.dumpItems = dumpItems;
        this.maxPage = this.calcMaxPage(dumpItems.size());
        this.openPage(PageAction.START);
    }

    private int calcMaxPage(int size) {
        return (int) Math.ceil(size / PAGE_SIZE);
    }

    @Override
    public void open(Player player) {
        super.open(player);
    }

    public void openPage(PageAction pageAction) {
        int nextPage = 0;
        switch (pageAction) {
            case START:
                nextPage = 0;
                break;
            case DECREMENT:
                nextPage = currentPage - 1;
                break;
            case INCREMENT:
                nextPage = currentPage + 1;
        }
        if (nextPage > this.maxPage || nextPage < 0) return;
        super.setContents(this.getItems(nextPage));
    }


    public DumpItem[] getItems(int pageIndex) {
        final int begin = pageIndex * PAGE_SIZE;
        int end = pageIndex * PAGE_SIZE + PAGE_SIZE;

        if (end >= this.dumpItems.size()) end = this.dumpItems.size();

        final DumpItem[] pageItems = this.dumpItems.subList(begin, end).toArray(new DumpItem[end]);
        final DumpItem[] finalItems = new DumpItem[54];
        System.arraycopy(pageItems, 0, finalItems, 0, pageItems.length);
        System.arraycopy(this.getNavigationItems(this.getPagePosition()), 0, finalItems, 45, 9);
        return finalItems;
    }

    public PagePosition getPagePosition() {
        if (this.maxPage == 0) return PagePosition.ONLY;
        if (currentPage == 0) return PagePosition.FIRST;
        else if (currentPage == this.maxPage) return PagePosition.LAST;
        else return PagePosition.MIDDLE;
    }

    public abstract DumpItem[] getNavigationItems(PagePosition pagePosition);


    public enum PagePosition {
        ONLY(),
        FIRST(),
        MIDDLE(),
        LAST();
    }

    public enum PageAction {
        START(),
        INCREMENT(),
        DECREMENT();
    }
}
