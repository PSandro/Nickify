package de.psandro.nickify.api.exception;

public class PlayerAlreadyNickedException extends NickifyException {
    public PlayerAlreadyNickedException(String name) {
        super("The player " + name + " is already nicked!");
    }
}
