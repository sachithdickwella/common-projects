package com.payconiq.geektastic.util.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

/**
 * Class to generalize the output of each of the endpoints from
 * {@link com.payconiq.geektastic.controller.StockController}.
 *
 * @param timestamp instance of {@link LocalDateTime} to specify when the response
 *                  created.
 * @param path      instance of {@link String} to denote request target path.
 * @param status    instance of {@link String} to denote HTTP response code.
 * @param message   instance of {@link String} to represent detailed message about
 *                  what had happened.
 * @param payload   instance of {@link T} type where the response is pointed to if
 *                  the request is a success, else null.
 * @version 1.0.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record Response<T>(@NotNull LocalDateTime timestamp,
                          @NotNull String path,
                          @NotNull Integer status,
                          @NotNull String message,
                          @NotNull T payload) {
}
