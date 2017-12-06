package de.psandro.nickify.controller.exception;

public class NoTeamNameAvailableException extends NickifyException {
    public NoTeamNameAvailableException(String message) {
        super(message);
    }

    public NoTeamNameAvailableException() {
        super();
    }
}
