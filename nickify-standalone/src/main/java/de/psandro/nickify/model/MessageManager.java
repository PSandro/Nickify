package de.psandro.nickify.model;

import de.psandro.nickify.controller.message.IMessageManager;
import de.psandro.nickify.controller.message.MessageFormat;
import de.psandro.nickify.controller.message.MessageId;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Set;

@AllArgsConstructor
public final class MessageManager implements IMessageManager {

    private final Set<MessageFormat> messageFormats;

    @Override
    public MessageFormat getMessageFormat(MessageId id) {
        return this.messageFormats.stream().filter(messageFormat -> messageFormat.getMessageId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public void sendIfPresent(Player player, MessageId id, Map<String, String> replaces) {
        final MessageFormat format = this.getMessageFormat(id);
        if (format == null) return;
        replaces.forEach((key, value) -> format.replace(key, value));
        player.sendMessage(format.buildMessage());
    }

    @Override
    public String getMessage(MessageId id) {
        final MessageFormat format = this.getMessageFormat(id);
        if (format == null) return null;
        return format.buildMessage();
    }
}
