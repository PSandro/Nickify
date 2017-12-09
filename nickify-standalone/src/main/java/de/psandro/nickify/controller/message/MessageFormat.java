package de.psandro.nickify.controller.message;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bukkit.ChatColor;

@AllArgsConstructor
@EqualsAndHashCode
public final class MessageFormat {

    private String rawMessage;
    @Getter
    private final MessageId messageId;


    public MessageFormat replace(String spacer, String value) {
        if (this.messageId.getSpacers().contains(spacer) && this.rawMessage.contains(spacer)) {
            this.rawMessage = this.rawMessage.replaceAll(spacer, value);
        }
        return this;
    }


    public String buildMessage() {
        return ChatColor.translateAlternateColorCodes('&', this.rawMessage);
    }

}
