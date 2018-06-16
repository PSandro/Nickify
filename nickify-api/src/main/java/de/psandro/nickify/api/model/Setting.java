package de.psandro.nickify.api.model;


import lombok.Getter;


public enum Setting {

    DEBUG_MODE(Boolean.class);

    @Getter
    private Class clazz;

    Setting(Class<?> clazz) {
        this.clazz = clazz;
    }
}
