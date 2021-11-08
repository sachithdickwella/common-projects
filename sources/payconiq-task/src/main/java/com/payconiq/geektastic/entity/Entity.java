package com.payconiq.geektastic.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
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
     * {@link UUID} to denote stock id.
     */
    private UUID id;
    /**
     * Instance of {@link LocalDateTime} to specify stock
     * created date and time.
     */
    private LocalDateTime createDateTime;
    /**
     * Instance of {@link LocalDateTime} to specify stock
     * last updated date time.
     */
    private LocalDateTime lastUpdatedDateTime;
    /**
     * {@link Boolean} to denote lock status if available.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean locked;
    /**
     * {@link Boolean} to denote if the locking attempted
     * and failed.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean lockFailed;
}
