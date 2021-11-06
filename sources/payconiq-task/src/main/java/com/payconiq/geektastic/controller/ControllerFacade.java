package com.payconiq.geektastic.controller;

import com.payconiq.geektastic.util.pojo.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller facade for all the subsequence controllers with CRUD operations.
 * <p>
 * Suppress the {@code "unused"} warnings hence the implementation will never
 * be invoked explicitly, but container would take care of that.
 *
 * @version 1.0.0
 */
@SuppressWarnings("unused")
public interface ControllerFacade<T> {

    /**
     * Method to create entity in the database or any other persistence store. If
     * the operation is success, would return newly created instance encapsulated
     * with {@link Response} class.
     *
     * @param payload instance of {@link T} which needed to be stored in the store.
     * @param request instance of {@link HttpServletRequest} to derive request details.
     * @return an instance of {@link Response} wrapped with {@link ResponseEntity}.
     */
    @NotNull ResponseEntity<Response<T>> create(@NotNull T payload, @NotNull HttpServletRequest request);

    /**
     * Method to update entity in the database or any other persistence store. If
     * the operation is success, would return updated instance encapsulated with
     * {@link Response} class.
     *
     * @param id      instance of {@link String} which denotes the entity id in the
     *                store.
     * @param payload instance of {@link T} which needed to be updated in the store.
     * @param request instance of {@link HttpServletRequest} to derive request details.
     * @return an instance of {@link Response} wrapped with {@link ResponseEntity}.
     */
    @NotNull ResponseEntity<Response<T>> update(@NotNull String id,
                                                @NotNull T payload,
                                                @NotNull HttpServletRequest request);

    /**
     * Method to fetch single entity from the database or any other persistence store.
     * If the operation is success, would return respective instance encapsulated with
     * {@link Response} class.
     *
     * @param id      instance of {@link String} which denotes the entity id in the store.
     * @param request instance of {@link HttpServletRequest} to derive request details.
     * @return an instance of {@link Response} wrapped with {@link ResponseEntity}.
     */
    @NotNull ResponseEntity<Response<T>> single(@NotNull String id, @NotNull HttpServletRequest request);

    /**
     * Method to fetch all the entities from the database or any other persistence store.
     * If the operation is success, would return all the instances encapsulated with
     * {@link Response} class.
     *
     * @param request instance of {@link HttpServletRequest} to derive request details.
     * @return an instance of {@link Response} wrapped with {@link ResponseEntity}.
     */
    @NotNull ResponseEntity<Response<T>> all(@NotNull HttpServletRequest request);

    /**
     * Method to delete single entity from the database or any other persistence store.
     * If the operation is success, would return deleted instance encapsulated with
     * {@link Response} class.
     *
     * @param id      instance of {@link String} which denotes the entity id in the store.
     * @param request instance of {@link HttpServletRequest} to derive request details.
     * @return an instance of {@link Response} wrapped with {@link ResponseEntity}.
     */
    @NotNull ResponseEntity<Response<T>> delete(@NotNull String id, @NotNull HttpServletRequest request);

    /**
     * Response builder method to create an instance of {@link Response} with single
     * payload value.
     *
     * @param request instance of {@link HttpServletRequest} to derive request related
     *                information.
     * @param status  instance of {@link HttpStatus} to denote HTTP status of the response.
     * @param message instance of {@link String} to specify custom message in the response.
     * @param payload instance of {@link T} that should include in the response payload.
     * @return a new instance of {@link Response} class with parameterized data from
     * the upstream.
     */
    @NotNull
    default Response<T> buildResponse(@NotNull HttpServletRequest request,
                                      @NotNull HttpStatus status,
                                      @NotNull String message,
                                      @NotNull T payload) {
        return new Response<>(LocalDateTime.now(),
                request.getRequestURI(),
                status,
                message,
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
     * @param payload instance of {@link List<T>} that should include in the response payload.
     * @return a new instance of {@link Response} class with parameterized data from
     * the upstream.
     */
    @NotNull
    default Response<T> buildResponse(@NotNull HttpServletRequest request,
                                      @NotNull HttpStatus status,
                                      @NotNull String message,
                                      @NotNull List<T> payload) {
        return new Response<>(LocalDateTime.now(),
                request.getRequestURI(),
                status,
                message,
                payload);
    }
}
