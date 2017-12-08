package de.psandro.nickify.controller.exception;

public final class PlayerNotNickedException extends NickifyException {
    public PlayerNotNickedException(String message) {
        super("The player " + message + " is not nicked!");
    }
}
