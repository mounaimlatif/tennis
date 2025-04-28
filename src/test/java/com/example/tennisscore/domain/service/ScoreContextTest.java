package com.example.tennisscore.domain.service;

import com.example.tennisscore.domain.model.GameEndEvent;
import com.example.tennisscore.domain.model.Player;
import com.example.tennisscore.domain.model.ScoreChangeEvent;
import com.example.tennisscore.domain.model.ScoreEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.tennisscore.domain.model.Player.PLAYER_A;
import static com.example.tennisscore.domain.model.Player.PLAYER_B;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit tests for ScoreContext")
class ScoreContextTest {

    private ScoreContext context;
    private Player playerA;
    private Player playerB;

    @BeforeEach
    void setUp() {
        context = new ScoreContext();
        playerA = Player.builder().name(PLAYER_A).points(0).advantage(false).build();
        playerB = Player.builder().name(PLAYER_B).points(0).advantage(false).build();
    }

    @Test
    @DisplayName("Should save score context correctly based on scoreItem")
    void shouldSaveScoreContextCorrectly() {
        // Act
        context.saveScoreContext('B', playerA, playerB);

        // Assert
        assertEquals(playerB, context.getSequenceWinner());
        assertEquals(playerA, context.getSequenceLoss());
    }

    @Test
    @DisplayName("Should end game correctly and record a GameEndEvent")
    void shouldEndGameCorrectly() {
        // Act
        context.endGame(playerA);

        // Assert
        assertTrue(context.isGameEnded());
        assertEquals(playerA, context.getSequenceWinner());

        List<ScoreEvent> events = context.getEvents();
        assertEquals(1, events.size());
        assertInstanceOf(GameEndEvent.class, events.getFirst());

        GameEndEvent event = (GameEndEvent) events.getFirst();
        assertEquals(playerA.getName(), event.getWinner().getName());

    }

    @Test
    @DisplayName("Should add a ScoreChangeEvent correctly")
    void shouldAddScoreChangeEventCorrectly() {
        // Arrange
        playerA.setPoints(15);
        playerB.setPoints(30);
        // Act
        context.addScoreChange(playerA, playerB);

        // Assert
        List<ScoreEvent> events = context.getEvents();
        assertEquals(1, events.size());
        assertInstanceOf(ScoreChangeEvent.class, events.getFirst(), "The event should be a ScoreChangeEvent");

        ScoreChangeEvent event = (ScoreChangeEvent) events.getFirst();
        assertEquals(playerA.getName(), event.getPlayerA().getName());
        assertEquals(playerA.getPoints(), event.getPlayerA().getPoints());
        assertEquals(playerA.isAdvantage(), event.getPlayerA().isAdvantage());
        assertEquals(playerB.getName(), event.getPlayerB().getName());
        assertEquals(playerB.getPoints(), event.getPlayerB().getPoints());
        assertEquals(playerB.isAdvantage(), event.getPlayerB().isAdvantage());
    }
}