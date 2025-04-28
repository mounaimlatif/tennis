package com.example.tennisscore.infrastructure.adapter.input.rest.validator;

import com.example.tennisscore.infrastructure.adapter.input.rest.data.GameRequest;
import com.example.tennisscore.infrastructure.adapter.output.exception.InvalidInputException;
import org.springframework.util.ObjectUtils;

import static com.example.tennisscore.infrastructure.adapter.output.exception.InvalidInputException.ERROR_INPUT_CANNOT_BE_BLANK;
import static com.example.tennisscore.infrastructure.adapter.output.exception.InvalidInputException.ERROR_INPUT_CANNOT_BE_NULL;

public class InputValidator {

    private InputValidator() {
        // Private constructor to prevent instantiation
    }
    public static void validateInput(GameRequest gameRequest) {
        if (gameRequest == null) {
            throw new InvalidInputException(ERROR_INPUT_CANNOT_BE_NULL);
        }

        if (ObjectUtils.isEmpty(gameRequest.input())) {
            throw new InvalidInputException(ERROR_INPUT_CANNOT_BE_BLANK);
        }

    }
}
