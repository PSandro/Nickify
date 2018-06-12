package de.psandro.nickify.exception;

import de.psandro.nickify.api.exception.NickifyException;

public final class ConfigurationException extends NickifyException {
    public ConfigurationException(String message) {
        super(message);
    }
}
