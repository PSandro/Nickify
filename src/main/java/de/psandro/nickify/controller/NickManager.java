package de.psandro.nickify.controller;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.UUID;

public interface NickManager {

    @Nullable
    Nickable getNickable(UUID uuid);

    Set<Nickable> getNickables();
}
