package de.psandro.nickify.api.model.exception;

import de.psandro.nickify.api.exception.NickifyException;
import de.psandro.nickify.api.model.MessageId;

public final class MessageFormatNotFoundException extends NickifyException {
    public MessageFormatNotFoundException(MessageId messageId) {
        super("No message with the id " + messageId.getId() + " was found!");
    }
}
