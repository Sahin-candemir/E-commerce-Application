package com.enoca.e_commerce.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    private String message;
    private int status;
}
