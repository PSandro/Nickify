package de.psandro.nickify.controller.message;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.bukkit.ChatColor;

import java.util.Set;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageEditor {

    private String message;
    private final Set<String> spacers;

    public MessageEditor replace(String spacer, String value) {
        if (this.spacers.contains(spacer) && this.message.contains(spacer)) {
            this.message = this.message.replaceAll(spacer, value);
        }
        return this;
    }


    public String buildMessage() {
        return ChatColor.translateAlternateColorCodes('&', this.message);
    }

}
