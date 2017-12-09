package de.psandro.nickify.view.inventory.inv;

import de.psandro.nickify.model.TeamViewPreset;
import de.psandro.nickify.view.inventory.AbstractDynamicInventory;
import de.psandro.nickify.view.inventory.AbstractMultisiteInventory;
import de.psandro.nickify.view.inventory.InventoryActionCallback;
import de.psandro.nickify.view.inventory.item.ClickableItem;
import de.psandro.nickify.view.inventory.item.DumpItem;
import de.psandro.nickify.view.inventory.item.ItemBuilder;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TeamViewPresetsInventory extends AbstractMultisiteInventory {

    private static final Comparator<TeamViewPreset> PRIORITY_COMPARATOR = Comparator.comparingInt(a -> a.getLayout().getPriority());

    public TeamViewPresetsInventory(AbstractDynamicInventory parent, Player holder, InventoryActionCallback inventoryActionCallback, Set<TeamViewPreset> presets) {
        super(parent, holder, "§6Nickify TeamView Presets", inventoryActionCallback, teamViewPresetsToItems(presets));
    }

    @Override
    public DumpItem[] getNavigationItems(PagePosition pagePosition) {
        return new DumpItem[]{
                (pagePosition == PagePosition.FIRST || pagePosition == PagePosition.ONLY) ? super.getParentButtonIfExist() : PREVIOUS_SITE_ITEM, null, null, null, null, null, null, null, (pagePosition == PagePosition.LAST || pagePosition == PagePosition.ONLY) ? null : NEXT_SITE_ITEM
        };

    }

    private static List<DumpItem> teamViewPresetsToItems(final @NonNull Set<TeamViewPreset> presets) {
        return presets.stream().sorted(PRIORITY_COMPARATOR).map(entry -> {
            final String name = "§7" + entry.getName();
            final String layout = entry.getLayout().getPrefix() + "Name" + entry.getLayout().getSuffix();
            final String permission = "§c" + entry.getPermission();
            final String priority = "§6" + entry.getLayout().getPriority();

            final ItemStack itemStack = new ItemBuilder(Material.BOOK)
                    .withName(name)
                    .withLore(
                            "§7layout: ",
                            layout,
                            " ",
                            "§7priority: ",
                            priority,
                            " ",
                            "§7permission: ",
                            permission
                    ).build();

            return new ClickableItem(itemStack, (player, clickType, context, inventory) -> {
                //Todo open inv
            });
        }).collect(Collectors.toList());
    }

}
