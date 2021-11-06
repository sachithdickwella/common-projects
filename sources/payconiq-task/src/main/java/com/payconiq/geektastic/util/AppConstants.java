package com.payconiq.geektastic.util;

import org.jetbrains.annotations.NotNull;

import java.time.format.DateTimeFormatter;

/**
 * Compile-time constants to access throughout the application lifecycle.
 *
 * @version 1.0.0
 */
public enum AppConstants {

    /**
     * Constant of default date time format of the datastore data.
     */
    CONST_DEFAULT_DATETIME_FORMAT(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss"));

    /**
     * Local-member variable to hold enum's attribute values.
     */
    private final Object instance;

    /**
     * No-args private constructor to initialize local-member variable
     * {@link #instance}.
     *
     * @param instance instance of {@link T} to store as enum attribute.
     */
    <T> AppConstants(@NotNull T instance) {
        this.instance = instance;
    }

    /**
     * Getter method for {@link #instance} local member to return as it
     * is.
     *
     * @return the local member variable {@link #instance} value.
     */
    public Object instance() {
        return instance;
    }

    /**
     * Getter method for {@link #instance} local member to return as a
     * given type if compatible.
     *
     * @param tClass instance of {@link Class<T>} to cast the local member
     *               {@link #instance}.
     * @return an instance of {@link T} which originally derive from local
     * member {@link #instance} variable.
     */
    public <T> T instance(@NotNull Class<T> tClass) {
        return tClass.cast(instance());
    }
}
