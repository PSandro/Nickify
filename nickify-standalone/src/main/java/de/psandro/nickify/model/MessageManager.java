package de.psandro.nickify.model;

import de.psandro.nickify.controller.message.IMessageManager;
import de.psandro.nickify.controller.message.MessageFormat;
import de.psandro.nickify.controller.message.MessageId;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Set;

public final class MessageManager implements IMessageManager {


    private final MessageEntity messageEntity;

    public MessageManager(MessageEntity messageEntity) {
        this.messageEntity = messageEntity;
    }

    @Override
    public MessageFormat getMessageFormat(MessageId id) {
        final MessageFormat messageFormat = this.messageEntity.getMessages().get(id);
        return messageFormat.isActive() ? messageFormat : null;
    }

    @Override
    public void sendIfPresent(Player player, MessageId id, Map<String, String> replaces) {
        final MessageFormat format = this.getMessageFormat(id);
        if (format == null) return;
        replaces.forEach((key, value) -> format.edit().replace(key, value));
        player.sendMessage(format.buildMessage());
    }

    @Override
    public String getMessage(MessageId id) {
        final MessageFormat format = this.getMessageFormat(id);
        if (format == null) return null;
        return format.buildMessage();
    }
}
