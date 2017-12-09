package de.psandro.nickify.model;

import de.psandro.nickify.controller.message.MessageFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@AllArgsConstructor
public final class MessageEntity {

    @Getter
    @Setter
    private Set<MessageFormat> messages;

}
