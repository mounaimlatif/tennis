package com.example.tennisscore.domain.service;

import com.example.tennisscore.domain.model.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StandardTennisStrategy implements ScoringStrategy {


    @Override
    public void applyScore(ScoreContext context) {
        if (context.isGameEnded()) return;

        if (shouldIncreaseScore(context.getSequenceWinner())) {
            applySimplePoint(context);
            return;
        }

        if (shouldWinDirectly(context.getSequenceLoss())) {
            context.endGame(context.getSequenceWinner());
            return;
        }

        applyDeuceLogic(context);
    }

    private boolean shouldIncreaseScore(Player player) {
        return player.getPoints() < 40;
    }

    private void applySimplePoint(ScoreContext context) {
        winBall(context.getSequenceWinner());
        context.addScoreChange(context.getSequenceWinner(), context.getSequenceLoss());
    }

    private boolean shouldWinDirectly(Player sequenceLoss) {
        return sequenceLoss.getPoints() < 40;
    }

    private void applyDeuceLogic(ScoreContext context) {
        if (context.getSequenceWinner().isAdvantage()) {
            context.endGame(context.getSequenceWinner());
            return;
        }

        if (context.getSequenceLoss().isAdvantage()) {
            removeAdvantageFromLoss(context);
            return;
        }

        giveAdvantageToWinner(context);
    }

    private void removeAdvantageFromLoss(ScoreContext context) {
        context.getSequenceLoss().resetAdvantage();
        context.addScoreChange(context.getSequenceWinner(), context.getSequenceLoss());
    }

    private void giveAdvantageToWinner(ScoreContext context) {
        context.getSequenceWinner().setAdvantage(true);
        context.addScoreChange(context.getSequenceWinner(), context.getSequenceLoss());
    }

    private void winBall(Player player) {
        if (player.getPoints() < 30) {
            player.setPoints(player.getPoints() + 15);
        } else if (player.getPoints() == 30) {
            player.setPoints(40);
        }
    }
}


