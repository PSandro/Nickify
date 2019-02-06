package eu.psandro.nickify.exception;

public class NickifyException extends RuntimeException {

    private static final long serialVersionUID = 8031454035329982978L;

    public NickifyException() {
        super();
    }

    public NickifyException(String message) {
        super(message);
    }
}
