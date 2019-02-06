package eu.psandro.nickify.exception;

public final class PlayerNotNickedException extends NickifyException {
    
    private static final long serialVersionUID = 4878857333854925064L;

    public PlayerNotNickedException(String message) {
        super("The player " + message + " is not nicked!");
    }
}
