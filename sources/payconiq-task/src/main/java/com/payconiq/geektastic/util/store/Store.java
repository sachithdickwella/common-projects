package com.payconiq.geektastic.util.store;

import java.util.List;

/**
 * Parent class implementation for all possible child stores.
 *
 * @version 1.0.0
 */
public interface Store<K, T> {

    /**
     *
     */
    int insert(T type);

    /**
     *
     */
    int update(T type);

    /**
     *
     */
    int delete(K key);

    /**
     *
     */
    T select(K key);

    /**
     *
     */
    List<T> select();
}
