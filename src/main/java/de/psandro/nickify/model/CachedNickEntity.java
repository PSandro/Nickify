package de.psandro.nickify.model;

import com.comphenix.protocol.wrappers.WrappedSignedProperty;
import com.google.common.collect.Multimap;
import de.psandro.nickify.controller.NickEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.UUID;


@AllArgsConstructor
public final class CachedNickEntity implements NickEntity {

    private final String name;
    private final Multimap<String, WrappedSignedProperty> profileProperties;
    @Setter
    @NonNull
    @Getter
    private long latestUse = 0;
    private final UUID uniqueId;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public UUID getUniqueId() {
        return this.uniqueId;
    }

    @Override
    public Multimap<String, WrappedSignedProperty> getProfileProperties() {
        return this.profileProperties;
    }
}
