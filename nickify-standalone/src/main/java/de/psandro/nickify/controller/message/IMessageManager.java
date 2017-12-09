package de.psandro.nickify.controller.message;

import org.bukkit.entity.Player;

import java.util.Map;

public interface IMessageManager {

    MessageFormat getMessageFormat(MessageId id);

    void sendIfPresent(Player player, MessageId id, Map<String, String> replaces);

    String getMessage(MessageId id);

}
