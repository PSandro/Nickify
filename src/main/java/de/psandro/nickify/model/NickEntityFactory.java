package de.psandro.nickify.model;


import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.google.common.base.Preconditions;
import de.psandro.nickify.controller.nick.INickEntityFactory;
import de.psandro.nickify.controller.nick.NickEntity;

import java.util.*;
import java.util.concurrent.ExecutionException;

public final class NickEntityFactory implements INickEntityFactory {

    private final SortedSet<CachedNickEntity> availableNickEntitys = new TreeSet<>(Comparator.comparingLong(CachedNickEntity::getLatestUse));

    public NickEntityFactory() {
    }

    public Optional<NickEntity> pickAny() {
        if (availableNickEntitys.isEmpty()) return Optional.empty();
        final NickEntity first = availableNickEntitys.first();
        availableNickEntitys.remove(first);
        return Optional.of(first);
    }

    public void returnNickEntity(final CachedNickEntity nickEntity) {
        this.availableNickEntitys.add(nickEntity);
    }

    //Do not run in main Thread
    public NickEntity getByUUID(UUID uuid) throws ExecutionException, InterruptedException {
        final Optional<CachedNickEntity> cached = this.availableNickEntitys.stream().filter(entity -> entity.getUniqueId().equals(uuid)).findAny();

        if (cached.isPresent()) {
            return cached.get();
        }

        final WrappedGameProfile profile = ProfileFetcher.fetchProfile(uuid).get();
        final NickEntity nickEntity = this.buildNickEntity(profile);

        return nickEntity;
    }

    @Deprecated
    //Do not run in main Thread
    public NickEntity getByName(String name) throws ExecutionException, InterruptedException {
        Preconditions.checkNotNull(name, "The name cannot be null!");
        Preconditions.checkArgument(name.length() <= 16 && name.length() >= 3, "The name has a invalid length!");

        final Optional<CachedNickEntity> cached = this.availableNickEntitys.stream().filter(entity -> entity.getName().equalsIgnoreCase(name)).findAny();

        if (cached.isPresent()) {
            return cached.get();
        }

        final WrappedGameProfile profile = ProfileFetcher.fetchProfile(name).get();
        final NickEntity nickEntity = this.buildNickEntity(profile);

        return nickEntity;
    }

    private NickEntity buildNickEntity(WrappedGameProfile gameProfile) {
        return new CachedNickEntity(gameProfile.getName(), gameProfile.getProperties(), 0, gameProfile.getUUID());
    }

}
