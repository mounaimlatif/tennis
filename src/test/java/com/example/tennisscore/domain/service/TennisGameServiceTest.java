package com.example.tennisscore.domain.service;

import com.example.tennisscore.application.port.output.DisplayScorePort;
import com.example.tennisscore.domain.model.GameInput;
import com.example.tennisscore.domain.model.GameOutput;
import com.example.tennisscore.domain.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("Unit tests for TennisGameService with Mocks")
@ExtendWith(MockitoExtension.class)
class TennisGameServiceTest {

    @Mock
    private ScoringStrategy strategy;

    @Mock
    private ScoreContext context;

    @Mock
    private DisplayScorePort displayScorePort;

    private TennisGameService tennisGameService;

    @BeforeEach
    void setUp() {
        tennisGameService = TennisGameService.builder()
                .strategy(strategy)
                .context(context)
                .displayScorePort(displayScorePort)
                .build();
    }

    @Test
    @DisplayName("Should play full sequence without early end and return expected output")
    void shouldPlayFullSequence() {
        // Arrange
        String sequence = "ABAB";
        GameInput input = GameInput.builder().input(sequence).build();

        when(context.isGameEnded()).thenReturn(false); // Never ends during play
        when(displayScorePort.result(context)).thenReturn("Expected Output");

        // Act
        GameOutput output = tennisGameService.play(input);

        // Assert
        verify(context, times(4)).saveScoreContext(anyChar(), any(Player.class), any(Player.class));
        verify(strategy, times(4)).applyScore(context);
        verify(displayScorePort).result(context); // once for system.out, once for return

        assertEquals("Expected Output", output.getOutput());
    }

    @Test
    @DisplayName("Should stop playing if the game ends during sequence")
    void shouldStopPlayingIfGameEnds() {
        // Arrange
        String sequence = "ABAB";
        GameInput input = GameInput.builder().input(sequence).build();

        // Game ends after 2 moves
        when(context.isGameEnded())
                .thenReturn(false) // after first move
                .thenReturn(false) // after second move
                .thenReturn(true); // before third move

        when(displayScorePort.result(context)).thenReturn("Game Over");

        // Act
        GameOutput output = tennisGameService.play(input);

        // Assert
        verify(context, atLeast(2)).saveScoreContext(anyChar(), any(Player.class), any(Player.class));
        verify(strategy, atLeast(2)).applyScore(context);
        verify(displayScorePort).result(context);

        assertEquals("Game Over", output.getOutput());
    }
}
