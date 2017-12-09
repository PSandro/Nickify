package de.psandro.nickify.controller.nick;

import de.psandro.nickify.model.CachedNickEntity;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface INickEntityFactory {

    NickEntity getByUUID(UUID uuid) throws ExecutionException, InterruptedException;

    NickEntity getByName(String uuid) throws ExecutionException, InterruptedException, TimeoutException;

    void returnNickEntity(CachedNickEntity nickEntity);

    Optional<NickEntity> pickAny();
}
