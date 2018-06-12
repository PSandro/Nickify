package de.psandro.nickify.api.exception;

public class NickifyException extends RuntimeException {

    public NickifyException() {
        super();
    }

    public NickifyException(String message) {
        super(message);
    }
}
