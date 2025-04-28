package com.example.tennisscore.domain.service;

import com.example.tennisscore.domain.model.GameEndEvent;
import com.example.tennisscore.domain.model.Player;
import com.example.tennisscore.domain.model.ScoreChangeEvent;
import com.example.tennisscore.domain.model.ScoreEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Getter
@RequiredArgsConstructor
public class ScoreContext {
    private boolean gameEnded = false;
    private Player sequenceWinner;
    private Player sequenceLoss;
    private final List<ScoreEvent> events = new ArrayList<>();

    public void endGame(Player winner) {
        this.gameEnded = true;
        this.sequenceWinner = winner;

        events.add(new GameEndEvent(winner));
    }


    public void addScoreChange(Player playerA, Player playerB) {
        events.add(new ScoreChangeEvent(playerA, playerB));
    }

    public void saveScoreContext(char scoreItem, Player playerA, Player playerB) {
        this.sequenceWinner = playerA;
        this.sequenceLoss = playerB;
        if (scoreItem != 'A') {
            this.sequenceWinner = playerB;
            this.sequenceLoss = playerA;
        }
    }


}
