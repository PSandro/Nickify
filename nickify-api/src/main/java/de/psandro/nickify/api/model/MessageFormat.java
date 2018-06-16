package de.psandro.nickify.api.model;


import de.psandro.nickify.api.model.MessageEditor;
import de.psandro.nickify.api.model.MessageId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
public final class MessageFormat extends UnsafeMessage {

    private String rawMessage;
    @Getter
    @Setter
    private boolean active;

    public MessageFormat(String rawMessage, MessageId messageId, boolean active) {
        super(messageId);
        this.rawMessage = rawMessage;
        this.active = active;
    }

    public MessageEditor edit() {
        return new MessageEditor(this.rawMessage, super.getMessageId().spacers);
    }


    public String buildMessage() {
        return this.rawMessage.replaceAll("&", "§");
    }

}
