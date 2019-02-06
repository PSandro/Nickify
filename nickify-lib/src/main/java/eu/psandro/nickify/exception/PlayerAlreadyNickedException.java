package eu.psandro.nickify.exception;

public class PlayerAlreadyNickedException extends NickifyException {
    
    private static final long serialVersionUID = -4630799537888897152L;

    public PlayerAlreadyNickedException(String name) {
        super("The player " + name + " is already nicked!");
    }
}
