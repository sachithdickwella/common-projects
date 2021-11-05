package com.payconiq.geektastic.util;

import org.jetbrains.annotations.NotNull;

/**
 * Compile-time constants to access throughout the application lifecycle.
 *
 * @version 1.0.0
 */
public enum AppConstants {

    ;

    /**
     *
     */
    private final Object instance;

    /**
     *
     */
    <T> AppConstants(@NotNull T instance) {
        this.instance = instance;
    }

    /**
     *
     */
    public Object instance() {
        return instance;
    }

    /**
     *
     */
    public <T> T instance(@NotNull Class<T> tClass) {
        return tClass.cast(instance());
    }
}
