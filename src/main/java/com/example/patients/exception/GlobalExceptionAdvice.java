package com.example.patients.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionAdvice {

    private static final String DEFAULT_MESSAGE = "Something went wrong. Please try again later!";
    private static final String NOT_FOUND_MESSAGE = "Entity not found!";

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ErrorBody> handle(EntityNotFoundException e) {
        return new ResponseEntity<>(
                ErrorBody.builder().message(NOT_FOUND_MESSAGE).build(),
                HttpStatus.NOT_FOUND
        );
    }

    /* PathVariab validation */
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<List<ErrorBody>> handle(ConstraintViolationException e) {
        return new ResponseEntity<>(
                e.getConstraintViolations().stream()
                        .map(ex -> ErrorBody.builder()
                                .message(ex.getMessage())
                                .build()
                        )
                        .collect(Collectors.toList()),
                HttpStatus.BAD_REQUEST
        );
    }

    /* ReqDto field validation */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<List<ErrorBody>> handle(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(
                e.getBindingResult().getFieldErrors().stream()
                        .map(ex -> ErrorBody.builder()
                                .message(ex.getDefaultMessage())
                                .build()
                        )
                        .collect(Collectors.toList()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorBody> handle(RuntimeException e) {
        return new ResponseEntity<>(
                ErrorBody.builder().message(DEFAULT_MESSAGE).build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
