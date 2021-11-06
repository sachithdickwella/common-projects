package com.payconiq.geektastic.util.store;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Parent class implementation for all possible child stores.
 *
 * @version 1.0.0
 */
public interface Store<K, T> {

    /**
     * Lifecycle method to perform operation during bean creation.
     */
    @SuppressWarnings("unused")
    void init();

    /**
     * Lifecycle method to perform operation during bean destruction.
     */
    @SuppressWarnings("unused")
    void dispose() throws IOException;

    /**
     * Insert operation handler for the store. Only handle single entry
     * at once.
     *
     * @param value to be inserted to the underline store, which ever it
     *              will be.
     */
    void insert(@NotNull T value);

    /**
     * Update operation handler for the store. Only handle single entry
     * at a time.
     *
     * @param value to be updated to the underline store, which ever it
     *              will be.
     */
    void update(@NotNull T value);

    /**
     * Delete operation handler for the store. Only handle single entry
     * at a time.
     *
     * @param key of the value which needs to be removed from the store.
     * @return {@link T} value which recently deleted from the store.
     * Return value is wrapped with {@link Optional} hence possibility
     * of null value.
     */
    @NotNull
    Optional<T> delete(@NotNull K key);

    /**
     * Select operation handler for the store. Only handle single entry
     * at a time by the key.
     *
     * @param key of the value which needs to be fetched from the store.
     * @return an instance of {@link T} respective to the {@link K} key
     * provided. Return value is wrapped with {@link Optional} hence the
     * possibility of null value.
     */
    @NotNull
    Optional<T> select(@NotNull K key);

    /**
     * Select operation handler for the store. Fetch all the entries of the store
     * at a time.
     *
     * @return a {@link List} of {@link T}s available in the store implementation.
     */
    @NotNull
    List<T> select();
}
