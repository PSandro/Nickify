package eu.psandro.nickify.nick;

import eu.psandro.nickify.CachedNickEntity;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface INickEntityFactory {

    CachedNickEntity getByUUID(UUID uuid) throws ExecutionException, InterruptedException;

    CachedNickEntity getByName(String uuid) throws ExecutionException, InterruptedException, TimeoutException;

    void returnNickEntity(CachedNickEntity nickEntity);

    Optional<CachedNickEntity> pickAny();
}
