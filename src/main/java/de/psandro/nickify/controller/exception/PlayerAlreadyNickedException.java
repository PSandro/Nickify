package de.psandro.nickify.controller.exception;

public class PlayerAlreadyNickedException extends NickifyException {
    public PlayerAlreadyNickedException(String name) {
        super("The player " + name + " is already nicked!");
    }
}
