package com.example.tennisscore.domain.service;

import com.example.tennisscore.domain.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.tennisscore.domain.model.Player.PLAYER_A;
import static com.example.tennisscore.domain.model.Player.PLAYER_B;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit tests for StandardTennisStrategy")
class StandardTennisStrategyTest {


    private StandardTennisStrategy strategy;

    private Player playerA;
    private Player playerB;
    private ScoreContext context;

    @BeforeEach
    void setUp() {
        strategy = new StandardTennisStrategy();

        playerA = Player.builder().name(PLAYER_A).points(0).advantage(false).build();
        playerB = Player.builder().name(PLAYER_B).points(0).advantage(false).build();
        context = new ScoreContext();
    }

    @Test
    @DisplayName("Should increase score when player points are less than 40")
    void shouldIncreaseScoreWhenPlayerPointsAreLessThan40() {
        // Arrange
        context.saveScoreContext('A', playerA, playerB);

        // Act
        strategy.applyScore(context);

        // Assert
        assertEquals(15, playerA.getPoints());
    }

    @Test
    @DisplayName("Should end game When opponent points are less than 40")
    void shouldEndGameWhenOpponentPointsAreLessThan40() {
        // Arrange
        playerB.setPoints(30);
        context.saveScoreContext('B', playerA, playerB);

        // Act
        strategy.applyScore(context);

        // Assert
        assertFalse(context.isGameEnded());
    }

    @Test
    @DisplayName("Should apply deuce logic when both players have 40 points")
    void shouldApplyDeuceLogicWhenBothPlayersHave40Points() {
        // Arrange
        playerA.setPoints(40);
        playerB.setPoints(40);
        context.saveScoreContext('A', playerA, playerB);

        // Act
        strategy.applyScore(context);

        // Assert
        assertTrue(playerA.isAdvantage());
    }

    @Test
    @DisplayName("Should remove advantage from losing player")
    void shouldRemoveAdvantageFromLosingPlayer() {
        // Arrange
        playerA.setPoints(40);
        playerB.setPoints(40);
        playerB.setAdvantage(true);
        context.saveScoreContext('A', playerA, playerB);

        // Act
        strategy.applyScore(context);

        // Assert
        assertFalse(playerB.isAdvantage());
    }

    @Test
    @DisplayName("Should end game when winner has advantage")
    void shouldEndGameWhenWinnerHasAdvantage() {
        // Arrange
        playerA.setPoints(40);
        playerB.setPoints(40);
        playerA.setAdvantage(true);
        context.saveScoreContext('A', playerA, playerB);

        // Act
        strategy.applyScore(context);

        // Assert
        assertTrue(context.isGameEnded());
    }
}