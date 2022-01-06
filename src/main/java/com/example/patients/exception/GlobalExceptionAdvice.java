package com.example.patients.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionAdvice {

    private static final String DEFAULT_MESSAGE = "Something went wrong. Please try again later!";
    private static final String BAD_REQUEST_MESSAGE = "Invalid parameters!";

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ErrorBody> handle(EntityNotFoundException e) {
        return new ResponseEntity<>(
                ErrorBody.builder()
                        .message(String.format("%s with ID %s doesn't exist!", e.getEntityType(), e.getEntityId()))
                        .build(),
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

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorBody> handle(IllegalArgumentException e) {
        return new ResponseEntity<>(
                ErrorBody.builder()
                        .message(e.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    /* Postman invalid params (eg passing null to path variables) */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorBody> handle(MethodArgumentTypeMismatchException e) {
        return new ResponseEntity<>(
                ErrorBody.builder()
                        .message(BAD_REQUEST_MESSAGE)
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorBody> handle(RuntimeException e) {
        return new ResponseEntity<>(
                ErrorBody.builder()
                        .message(DEFAULT_MESSAGE)
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
