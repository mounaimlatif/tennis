package com.example.tennisscore.domain.service;

import com.example.tennisscore.application.port.input.TennisGamePort;
import com.example.tennisscore.application.port.output.DisplayScorePort;
import com.example.tennisscore.domain.model.GameInput;
import com.example.tennisscore.domain.model.GameOutput;
import com.example.tennisscore.domain.model.Player;
import com.example.tennisscore.domain.validation.GameInputValidator;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.tennisscore.domain.model.Player.PLAYER_A;
import static com.example.tennisscore.domain.model.Player.PLAYER_B;

@Service
@Builder
@RequiredArgsConstructor
public class TennisGameService implements TennisGamePort {


    private final Player playerA = Player.builder().name(PLAYER_A).advantage(false).points(0).build();
    private final Player playerB = Player.builder().name(PLAYER_B).advantage(false).points(0).build();
    private final ScoringStrategy strategy;
    private final ScoreContext context;
    private final DisplayScorePort displayScorePort;

    @Override
    public GameOutput play(GameInput input) {
        GameInputValidator.validate(input);
        for (char scoreItem : input.getInput().toCharArray()) {
            if (context.isGameEnded()) {
                break;
            }
            context.saveScoreContext(scoreItem, playerA, playerB);
            strategy.applyScore(context);
        }
        return GameOutput.builder().output(displayScorePort.result(context)).build();
    }

}
