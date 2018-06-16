package de.psandro.nickify.view.inventory.inv;

import de.psandro.nickify.api.model.MessageFormat;
import de.psandro.nickify.api.model.MessageId;
import de.psandro.nickify.api.model.UnsafeMessage;
import de.psandro.nickify.model.ConfigManager;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class MessagesViewInventory extends AbstractMultisiteInventory {

    private static final Comparator<UnsafeMessage> NAME_COMPARATOR = (Comparator.comparing(o -> o.getMessageId().getId()));

    private final ConfigManager configManager;

    public MessagesViewInventory(AbstractDynamicInventory parent, Player holder, InventoryActionCallback inventoryActionCallback, ConfigManager configManager) {
        super(parent, holder, "§6Nickify Messages View", inventoryActionCallback, unsafeMessagesToItems(getAllMessages(configManager)));
        this.configManager = configManager;
    }

    @Override
    public DumpItem[] getNavigationItems(PagePosition pagePosition) {
        return new DumpItem[]{
                (pagePosition == PagePosition.FIRST || pagePosition == PagePosition.ONLY) ? super.getParentButtonIfExist() : PREVIOUS_SITE_ITEM, null, null, null, null, null, null, null, (pagePosition == PagePosition.LAST || pagePosition == PagePosition.ONLY) ? null : NEXT_SITE_ITEM
        };

    }

    @Override
    public void refresh(List<DumpItem> dumpItems) {
        super.refresh(dumpItems);
    }

    public void refresh() {
        super.refresh(unsafeMessagesToItems(getAllMessages(configManager)));
    }


    private static List<DumpItem> unsafeMessagesToItems(final @NonNull Set<UnsafeMessage> messages) {
        return messages.stream().sorted(NAME_COMPARATOR).map(entry -> {
            return new ClickableItem<>(messageToItemStack(entry), (player, clickType, context, inventory) -> {
                inventory.closeSilent(player);
                new MessageInfoInventory(inventory, player, context.getActionListener(), entry, context.getConfigManager()).open(player);
            });
        }).collect(Collectors.toList());
    }

    public static ItemStack messageToItemStack(UnsafeMessage unsafeMessage) {
        boolean exists = unsafeMessage instanceof MessageFormat;
        String[] lore;
        final String name = "§7" + unsafeMessage.getMessageId().getId();
        final String status = exists ? "§aexisting" : "§cnot existing";
        if (exists) {

            final String spacer = "§e" + unsafeMessage.getMessageId().getSpacers().stream().collect(Collectors.joining("§7, §e"));
            final String value = exists ? ((MessageFormat) unsafeMessage).buildMessage() : "§cnothing";
            final String active = ((MessageFormat) unsafeMessage).isActive() ? "§aactivated" : "§cdeactivated";
            lore = new String[]{"§7status: ",
                    status,
                    " ",
                    "§7available spacers: ",
                    spacer,
                    " ",
                    "§7current value:",
                    value,
                    " ",
                    "§7activation:",
                    active};
        } else lore = new String[]{"§7status: ",
                status};

        return new ItemBuilder(exists ? (((MessageFormat) unsafeMessage).isActive() ? Material.PAPER : Material.INK_SACK) : Material.BARRIER)
                .withName(name)
                .withLore(lore).build();

    }

    private static Set<UnsafeMessage> getAllMessages(ConfigManager configManager) {
        final Set<UnsafeMessage> unsafeMessages = new HashSet<>(configManager.getMessageEntity().getMessages().values());
        final Set<MessageId> existing = configManager.getMessageEntity().getMessages().keySet();
        for (MessageId messageId : MessageId.values()) {
            if (!existing.contains(messageId)) {
                unsafeMessages.add(new UnsafeMessage(messageId));
            }
        }
        return unsafeMessages;
    }

}
