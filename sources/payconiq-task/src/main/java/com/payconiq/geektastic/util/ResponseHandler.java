package com.payconiq.geektastic.util;

import com.payconiq.geektastic.util.pojo.Response;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.function.Supplier;

/**
 * Class to handle {@link ResponseEntity} related operations. By implementing
 * this interface, any class inherits ability to manipulate {@code Response}s
 * and {@link ResponseEntity}s.
 *
 * @version 1.0.0
 */
public abstract class ResponseHandler<V, T extends Response<V>> {

    /**
     * Instance of {@link Logger} initialized by the subclass implementation.
     */
    protected final Logger logger;

    /**
     * Single-args constructor to initialize local {@link #logger} instance for
     * logging.
     *
     * @param logger from the child class implementation for logging.
     */
    protected ResponseHandler(@NotNull Logger logger) {
        this.logger = logger;
    }

    /**
     * Generate {@link ResponseEntity} instance with given response information
     * via {@link Supplier} and return with respective details.
     *
     * @param supplier instance of {@link Supplier} to feed response details from
     *                 upstream class.
     * @return an instance of {@link ResponseEntity} containing the response info.
     */
    @NotNull
    protected ResponseEntity<T> handle(@NotNull Supplier<T> supplier) {
        var type = supplier.get();
        var responseEntity = ResponseEntity.status(HttpStatus.valueOf(type.status())).body(type);

        logger.info("Response : {}", responseEntity);
        return responseEntity;
    }
}
