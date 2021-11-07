package com.payconiq.geektastic.config;

import com.payconiq.geektastic.util.pojo.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import static com.payconiq.geektastic.util.pojo.Response.buildResponse;

/**
 * Class to handle known exceptions thrown from controllers and response
 * back with appropriate content.
 *
 * @version 1.0.0
 */
@ControllerAdvice
public class ResponseExceptionHandler {

    /**
     *
     */
    @NotNull
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response<Void>> handleInvalidId(@NotNull IllegalArgumentException ex,
                                                          @NotNull HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildResponse(request,
                        HttpStatus.BAD_REQUEST,
                        ex.getMessage(),
                        null,
                        null));
    }
}
