package com.tcc.entrepaginas.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFound e, HttpServletRequest request) {
        String error = "Resource not found";
        HttpStatus status = HttpStatus.NOT_FOUND;

        log.error("ResourceNotFound: URI={}, message={}", request.getRequestURI(), e.getMessage());

        StandardError err =
                new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request) {
        String error = "Database error";
        HttpStatus status = HttpStatus.BAD_REQUEST;

        log.error("DatabaseException: URI={}, message={}", request.getRequestURI(), e.getMessage());

        StandardError err =
                new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<StandardError> handleCustomException(CustomException e, HttpServletRequest request) {
        String error = "Custom error";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        log.error("CustomException: URI={}, message={}", request.getRequestURI(), e.getMessage());

        StandardError err =
                new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }
}
