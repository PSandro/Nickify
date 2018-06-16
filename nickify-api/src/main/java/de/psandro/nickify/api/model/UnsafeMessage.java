package de.psandro.nickify.api.model;

import de.psandro.nickify.api.model.MessageId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UnsafeMessage {

    private final MessageId messageId;

}
