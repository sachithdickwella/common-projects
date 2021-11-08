package com.payconiq.geektastic.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Super type for all the entity types of the program to
 * manage common behavior.
 *
 * @version 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Entity {

    /**
     * {@link UUID} to denote stock id
     */
    private UUID id;
    /**
     * instance of {@link LocalDateTime} to specify stock
     * created date and time.
     */
    private LocalDateTime createDateTime;
    /**
     * instance of {@link LocalDateTime} to specify stock
     * last updated date time.
     */
    private LocalDateTime lastUpdatedDateTime;
}
