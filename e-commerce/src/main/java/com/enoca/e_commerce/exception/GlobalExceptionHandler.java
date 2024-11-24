package com.enoca.e_commerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmptyCartException.class)
    public ResponseEntity<ErrorResponse> handle(EmptyCartException exception){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(ResourceNotFoundException exception){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponse> handle(InsufficientStockException exception){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setStatus(HttpStatus.CONFLICT.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}
