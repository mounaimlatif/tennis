package com.example.tennisscore.domain.validation;

import com.example.tennisscore.domain.exception.InvalidGameInputException;
import com.example.tennisscore.domain.model.GameInput;

import static com.example.tennisscore.domain.exception.InvalidGameInputException.ERROR_GAME_INVALID_INPUT;

public class GameInputValidator {

    private static final String VALID_PATTERN = "^[AB]+$";

    private GameInputValidator() {
        // private constructor to prevent instantiation
    }

    public static void validate(GameInput input) {
        if (!input.getInput().matches(VALID_PATTERN)) {
            throw new InvalidGameInputException(ERROR_GAME_INVALID_INPUT);
        }
    }
}