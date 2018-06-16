package de.psandro.nickify.api.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

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
        return this.message.replaceAll("&", "§");
    }
}
