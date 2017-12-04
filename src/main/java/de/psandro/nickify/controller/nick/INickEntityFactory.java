package de.psandro.nickify.controller.nick;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public interface INickEntityFactory {

    NickEntity getByUUID(UUID uuid) throws ExecutionException, InterruptedException;

    NickEntity getByName(String uuid) throws ExecutionException, InterruptedException;

    Optional<NickEntity> pickAny();
}
