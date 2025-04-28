package com.example.tennisscore.infrastructure.adapter.output.formatter;


import com.example.tennisscore.application.port.output.DisplayScorePort;
import com.example.tennisscore.domain.model.GameEndEvent;
import com.example.tennisscore.domain.model.PlayerSnapshot;
import com.example.tennisscore.domain.model.ScoreChangeEvent;
import com.example.tennisscore.domain.model.ScoreEvent;
import com.example.tennisscore.domain.service.ScoreContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import static com.example.tennisscore.domain.model.Player.PLAYER_A;
import static com.example.tennisscore.domain.model.Player.PLAYER_B;

@Service
@Log4j2
public class DisplayScoreService implements DisplayScorePort {

    public static final String ADVANTAGE_PLAYER = "Advantage ";
    public static final String DEUCE = "Deuce ";


    private String displayScore(PlayerSnapshot playerA, PlayerSnapshot playerB) {
        if (log.isDebugEnabled()) {
            log.debug("Calculating score display for Player A: {} points, Player B: {} points", playerA.getPoints(), playerB.getPoints());
        }
        if (isDeuce(playerA, playerB)) {
            log.info("Deuce or Advantage detected for Player A: {}, Player B: {}", playerA, playerB);
            return displayDeuceOrAdvantage(playerA, playerB);
        }
        return displayRegularScore(playerA, playerB);
    }

    private boolean isDeuce(PlayerSnapshot playerA, PlayerSnapshot playerB) {
        boolean deuce = playerA.getPoints() >= 40 && playerB.getPoints() >= 40;
        log.debug("Checking for deuce: {}", deuce);
        return deuce;
    }

    private String displayDeuceOrAdvantage(PlayerSnapshot playerA, PlayerSnapshot playerB) {
        if (playerA.isAdvantage()) {
            log.info("Player A has advantage");
            return ADVANTAGE_PLAYER + PLAYER_A;
        }
        if (playerB.isAdvantage()) {
            log.info("Player B has advantage");
            return ADVANTAGE_PLAYER + PLAYER_B;
        }
        log.info("Deuce detected");
        return DEUCE;
    }

    private String displayRegularScore(PlayerSnapshot playerA, PlayerSnapshot playerB) {
        String score = String.format("%s : %s / %s : %s", PLAYER_A, score(playerA), PLAYER_B, score(playerB));
        log.debug("Regular score display: {}", score);
        return score;
    }

    private String score(PlayerSnapshot player) {
        int points = player.getPoints();
        return switch (points) {
            case 0 -> "Love";
            case 15 -> "15";
            case 30 -> "30";
            case 40 -> "40";
            default -> "Invalid Score"; // Cas de sécurité
        };
    }

    @Override
    public String result(ScoreContext context) {
        StringBuilder output = new StringBuilder();

        for (ScoreEvent event : context.getEvents()) {
            // possible d'utiliser le pattern visitor
            if (event instanceof ScoreChangeEvent scoreChange) {
                output.append(displayScore(scoreChange.getPlayerA(), scoreChange.getPlayerB())).append("\n");
            } else if (event instanceof GameEndEvent gameEnd) {
                output.append("Player ").append(gameEnd.getWinner().getName()).append(" wins the game\n");
            }
        }
        log.info("Result generation completed");
        return output.toString();
    }
}
