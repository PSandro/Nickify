package eu.psandro.nickify;


import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.google.common.base.Preconditions;
import eu.psandro.nickify.nick.INickEntityFactory;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class NickEntityFactory implements INickEntityFactory {

    private final SortedSet<CachedNickEntity> availableNickEntitys = new TreeSet<>(Comparator.comparingLong(CachedNickEntity::getLatestUse));

    public NickEntityFactory() {
    }

    public Optional<CachedNickEntity> pickAny() {
        if (availableNickEntitys.isEmpty()) return Optional.empty();
        final CachedNickEntity first = availableNickEntitys.first();
        availableNickEntitys.remove(first);
        return Optional.of(first);
    }

    @Override
    public void returnNickEntity(final CachedNickEntity nickEntity) {
        this.availableNickEntitys.add(nickEntity);
    }


    //Do not run in main Thread
    @Override
    public CachedNickEntity getByUUID(UUID uuid) throws ExecutionException, InterruptedException {
        final Optional<CachedNickEntity> cached = this.availableNickEntitys.stream().filter(entity -> entity.getFakeUniqueId().equals(uuid)).findAny();

        if (cached.isPresent()) {
            return cached.get();
        }

        final WrappedGameProfile profile = ProfileFetcher.fetchProfile(uuid).get();
        final CachedNickEntity nickEntity = this.buildNickEntity(profile);
        return nickEntity;
    }

    @Override
    @Deprecated
    //Do not run in main Thread
    public CachedNickEntity getByName(String name) throws ExecutionException, InterruptedException, TimeoutException {
        Preconditions.checkNotNull(name, "The name cannot be null!");
        Preconditions.checkArgument(name.length() <= 16 && name.length() >= 3, "The name has a invalid length!");

        final Optional<CachedNickEntity> cached = this.availableNickEntitys.stream().filter(entity -> entity.getFakeName().equalsIgnoreCase(name)).findAny();

        if (cached.isPresent()) {
            return cached.get();
        }

        final WrappedGameProfile profile = ProfileFetcher.fetchProfile(name).get(5, TimeUnit.SECONDS);
        final CachedNickEntity nickEntity = this.buildNickEntity(profile);
        return nickEntity;
    }


    private CachedNickEntity buildNickEntity(WrappedGameProfile gameProfile) {
        return new CachedNickEntity(gameProfile, 0);
    }

}
