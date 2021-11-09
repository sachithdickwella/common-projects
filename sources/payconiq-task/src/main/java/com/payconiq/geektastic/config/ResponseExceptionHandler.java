package com.payconiq.geektastic.config;

import com.payconiq.geektastic.util.ResponseHandler;
import com.payconiq.geektastic.util.pojo.Response;
import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import static com.payconiq.geektastic.util.pojo.Response.createResponse;

/**
 * Class to handle known exceptions thrown from controllers and response
 * back with appropriate content.
 *
 * @version 1.0.0
 */
@ControllerAdvice
public class ResponseExceptionHandler extends ResponseHandler<Void, Response<Void>> {

    /**
     * No-args constructor to initialize abstract super-class with {@code Logger}
     * instance for response logging.
     */
    public ResponseExceptionHandler() {
        super(LogManager.getLogger(ResponseExceptionHandler.class));
    }

    /**
     * Handle invalid ids and null values from the store due to data corruptions and return appropriate
     * response.
     *
     * @param ex instance of {@link RuntimeException} to handle incoming {@link IllegalArgumentException}
     *           and {@link NullPointerException}.
     * @return an instance of {@link ResponseEntity} with corresponding response information for the issue.
     */
    @NotNull
    @ExceptionHandler({
            IllegalArgumentException.class,
            NullPointerException.class,
            HttpMessageNotReadableException.class
    })
    public ResponseEntity<Response<Void>> handleInvalidIdAndNull(@NotNull RuntimeException ex,
                                                                 @NotNull HttpServletRequest request) {
        logger.error("Exception caught on global exception mapper", ex);
        return handle(() -> createResponse(request, HttpStatus.BAD_REQUEST, ex.getMessage(), null, null));
    }
}
