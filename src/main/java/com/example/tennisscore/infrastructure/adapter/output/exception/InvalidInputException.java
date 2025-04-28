package com.example.tennisscore.infrastructure.adapter.output.exception;

import lombok.Getter;

@Getter
public class InvalidInputException extends RuntimeException {
    public static final String ERROR_INPUT_CANNOT_BE_NULL = "Request cannot be null";
    public static final String ERROR_INPUT_CANNOT_BE_BLANK = "Input cannot be blank";

    public InvalidInputException(String message) {
        super(message);
    }
}
