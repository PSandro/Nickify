package de.psandro.nickify.model;

import de.psandro.nickify.controller.message.IMessageManager;
import de.psandro.nickify.controller.message.MessageEditor;
import de.psandro.nickify.controller.message.MessageFormat;
import de.psandro.nickify.controller.message.MessageId;
import org.bukkit.entity.Player;

import java.util.Map;

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
        final MessageEditor editor = format.edit();
        replaces.forEach(editor::replace);
        player.sendMessage(editor.buildMessage());
    }

    @Override
    public String getMessage(MessageId id) {
        final MessageFormat format = this.getMessageFormat(id);
        if (format == null) return null;
        return format.buildMessage();
    }
}
