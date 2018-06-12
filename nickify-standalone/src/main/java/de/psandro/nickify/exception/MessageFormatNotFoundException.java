package de.psandro.nickify.exception;

import de.psandro.nickify.api.exception.NickifyException;
import de.psandro.nickify.controller.message.MessageId;

public final class MessageFormatNotFoundException extends NickifyException {
    public MessageFormatNotFoundException(MessageId messageId) {
        super("No message with the id " + messageId.getId() + " was found!");
    }
}
