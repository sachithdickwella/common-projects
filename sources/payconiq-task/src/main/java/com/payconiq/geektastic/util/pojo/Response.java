package com.payconiq.geektastic.util.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Class to generalize the output of each of the endpoints from
 * {@link com.payconiq.geektastic.controller.StockController}.
 *
 * @param timestamp instance of {@link LocalDateTime} to specify when the response
 *                  created.
 * @param path      instance of {@link String} to denote request target path.
 * @param status    instance of {@link HttpStatus} to denote HTTP response code.
 * @param message   instance of {@link String} to represent detailed message about
 *                  what had happened.
 * @param payload   instance of {@link T} type where the response is pointed to if
 *                  the request is a success, else null.
 * @version 1.0.0
 */
@JacksonXmlRootElement(localName = "response")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record Response<T>(
        @NotNull
        @JacksonXmlProperty(localName = "timestamp")
        LocalDateTime timestamp,
        @NotNull
        @JacksonXmlProperty(localName = "path")
        String path,
        @NotNull
        @JacksonXmlProperty(localName = "status")
        HttpStatus status,
        @NotNull
        @JacksonXmlProperty(localName = "message")
        String message,
        @NotNull
        Integer count,
        @NotNull
        @JacksonXmlElementWrapper(localName = "payload")
        @JacksonXmlProperty(localName = "item")
        List<T> payload) {
}
