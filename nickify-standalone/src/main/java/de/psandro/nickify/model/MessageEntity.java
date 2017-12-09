package de.psandro.nickify.model;

import de.psandro.nickify.controller.message.MessageFormat;
import de.psandro.nickify.controller.message.MessageId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@AllArgsConstructor
public final class MessageEntity {

    @Getter
    @Setter
    private Map<MessageId, MessageFormat> messages;

    public void setMessage(MessageFormat format) {
        this.messages.put(format.getMessageId(), format);
    }

    public Set<MessageFormat> getActiveMessages() {
        return this.messages.values().stream().filter(MessageFormat::isActive).collect(Collectors.toSet());
    }

}
