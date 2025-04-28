package com.example.tennisscore.domain.exception;

public class InvalidGameInputException extends RuntimeException {

    public static final String ERROR_GAME_INVALID_INPUT="Input must only contain characters A or B";

    public InvalidGameInputException(String message) {
        super(message);
    }
}
