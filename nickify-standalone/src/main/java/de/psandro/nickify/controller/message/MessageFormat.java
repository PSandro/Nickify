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


    public MessageEditor edit(){
        return new MessageEditor(this.rawMessage, this.messageId.spacers);
    }


    public String buildMessage() {
        return ChatColor.translateAlternateColorCodes('&', this.rawMessage);
    }

}
