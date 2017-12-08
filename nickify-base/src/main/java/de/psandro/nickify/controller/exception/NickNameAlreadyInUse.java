package de.psandro.nickify.controller.exception;

public class NickNameAlreadyInUse extends NickifyException {

    public NickNameAlreadyInUse(String name) {
        super("The nickname " + name + " is already in use.");
    }
}
