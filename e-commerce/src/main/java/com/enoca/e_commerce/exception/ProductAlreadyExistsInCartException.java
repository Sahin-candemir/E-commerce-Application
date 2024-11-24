package com.enoca.e_commerce.exception;

public class ProductAlreadyExistsInCartException extends RuntimeException {
    public ProductAlreadyExistsInCartException(String message) {
        super(message);
    }
}
