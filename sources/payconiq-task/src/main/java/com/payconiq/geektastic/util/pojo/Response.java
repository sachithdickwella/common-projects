package com.payconiq.geektastic.util.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Class to generalize the output of each of the endpoints from
 * {@link com.payconiq.geektastic.controller.StockController}.
 *
 * @param timestamp instance of {@link LocalDateTime} to specify when the response
 *                  created.
 * @param path      instance of {@link String} to denote request target path.
 * @param status    instance of {@code int} to denote HTTP response code.
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
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSxxx")
        @JacksonXmlProperty(localName = "timestamp")
        ZonedDateTime timestamp,
        @NotNull
        @JacksonXmlProperty(localName = "path")
        String path,
        @JacksonXmlProperty(localName = "status")
        int status,
        @Nullable
        @JacksonXmlProperty(localName = "message")
        String message,
        @Nullable
        Integer count,
        @Nullable
        @JacksonXmlElementWrapper(localName = "payload")
        @JacksonXmlProperty(localName = "item")
        List<T> payload) {

    /**
     * Response builder method to create an instance of {@link Response} with single
     * payload value.
     *
     * @param request instance of {@link HttpServletRequest} to derive request related
     *                information.
     * @param status  instance of {@link HttpStatus} to denote HTTP status of the response.
     * @param message instance of {@link String} to specify custom message in the response.
     * @param count   of elements either fetched, modified or created.
     * @param payload instance of {@link T} that should include in the response payload.
     * @return a new instance of {@link Response} class with parameterized data from
     * the upstream.
     */
    @NotNull
    public static <T> Response<T> createResponse(@NotNull HttpServletRequest request,
                                                 @NotNull HttpStatus status,
                                                 @NotNull String message,
                                                 @NotNull Integer count,
                                                 @NotNull T payload) {
        return new Response<>(ZonedDateTime.now(),
                request.getRequestURI(),
                status.value(),
                message,
                count,
                List.of(payload));
    }

    /**
     * Response builder method to create an instance of {@link Response} with {@link List}
     * of payload values.
     *
     * @param request instance of {@link HttpServletRequest} to derive request related
     *                information.
     * @param status  instance of {@link HttpStatus} to denote HTTP status of the response.
     * @param message instance of {@link String} to specify custom message in the response.
     * @param count   of elements either fetched, modified or created.
     * @param payload instance of {@link List<T>} that should include in the response payload.
     * @return a new instance of {@link Response} class with parameterized data from
     * the upstream.
     */
    @NotNull
    public static <T> Response<T> createResponse(@NotNull HttpServletRequest request,
                                                 @NotNull HttpStatus status,
                                                 @NotNull String message,
                                                 @Nullable Integer count,
                                                 @Nullable List<T> payload) {
        return new Response<>(ZonedDateTime.now(),
                request.getRequestURI(),
                status.value(),
                message,
                count,
                payload);
    }
}
