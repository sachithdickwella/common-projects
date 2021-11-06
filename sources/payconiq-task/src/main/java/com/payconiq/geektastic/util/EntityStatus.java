package com.payconiq.geektastic.util;

/**
 * Compile-time constants for store entity statuses.
 *
 * @version 1.0.0
 */
public enum EntityStatus {

    /**
     * Entity status, when it's newly created.
     */
    NEW,
    /**
     * Entity status, when it's updated.
     */
    UPDATED,
    /**
     * Entity status, when it's locked for modification.
     */
    LOCKED,
    /**
     * Entity status, when an entity with same id exists.
     */
    DUPLICATE,
    /**
     * Entity status, when an entity not found to update.
     */
    NOT_FOUND;
}
