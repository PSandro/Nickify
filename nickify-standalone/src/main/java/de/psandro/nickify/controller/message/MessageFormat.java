package de.psandro.nickify.controller.message;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;

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
        return ChatColor.translateAlternateColorCodes('&', this.rawMessage);
    }

}
