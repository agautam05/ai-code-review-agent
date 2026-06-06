package com.aman.codereview.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            RuntimeException.class
    )
    public ResponseEntity<String> handleRuntimeException(
            RuntimeException exception
    ) {

        return ResponseEntity
                .status(
                        HttpStatus.BAD_REQUEST
                )
                .body(
                        exception.getMessage()
                );
    }

    @ExceptionHandler(
            Exception.class
    )
    public ResponseEntity<String> handleException(
            Exception exception
    ) {

        return ResponseEntity
                .status(
                        HttpStatus.INTERNAL_SERVER_ERROR
                )
                .body(
                        "Something went wrong"
                );
    }
}