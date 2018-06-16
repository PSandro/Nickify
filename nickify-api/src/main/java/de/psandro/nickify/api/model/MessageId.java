package de.psandro.nickify.api.model;

import com.google.common.collect.Sets;
import lombok.Getter;

import java.util.Set;

public enum MessageId {

    NO_PERMISSION(Sets.newHashSet(MessageSpacer.COMMAND_SPACER)),
    NICK(Sets.newHashSet(MessageSpacer.NICKNAME_SPACER)),
    UNNICK(Sets.newHashSet()),
    ALREADY_NICKED(Sets.newHashSet()),
    UNKNOWN_ERROR(Sets.newHashSet());


    @Getter
    final Set<String> spacers;

    MessageId(Set<String> spacers) {
        this.spacers = spacers;
    }

    public String getId() {
        return this.name();
    }


    public static class MessageSpacer {
        public static final String PLAYER_SPACER = "%player%";
        public static final String NICKNAME_SPACER = "%nickname%";
        public static final String PREFIX_SPACER = "%prefix%";
        public static final String SUFFIX_SPACER = "%suffix%";
        public static final String COMMAND_SPACER = "%command%";
        public static final String ERROR = "%error%";
    }

}
