package de.psandro.nickify.controller.exception;

public class NickifyException extends RuntimeException {

    public NickifyException() {
        super();
    }

    public NickifyException(String message) {
        super(message);
    }
}
