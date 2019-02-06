package eu.psandro.nickify.exception;

public class NickNameAlreadyInUse extends NickifyException {

    private static final long serialVersionUID = 2733744936871444488L;

    public NickNameAlreadyInUse(String name) {
        super("The nickname " + name + " is already in use.");
    }
}
