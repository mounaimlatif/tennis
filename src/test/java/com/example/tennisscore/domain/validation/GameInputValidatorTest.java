package com.example.tennisscore.domain.validation;

import com.example.tennisscore.domain.model.GameInput;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("Tests for GameInputValidator")
class GameInputValidatorTest {

    @ParameterizedTest
    @DisplayName("Should validate when input is valid (only contains A and B)")
    @ValueSource(strings = {"A", "B", "AA", "ABAB", "BAAB"})
    void shouldValidateCorrectInputs(String validInput) {
        // Given
        GameInput input = GameInput.builder()
                .input(validInput)
                .build();

        // When / Then
        assertThatCode(() -> GameInputValidator.validate(input))
                .doesNotThrowAnyException();
    }

}
