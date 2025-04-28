package com.example.tennisscore.domain.model;

import lombok.Getter;

@Getter
public class ScoreChangeEvent implements ScoreEvent {
    private final PlayerSnapshot playerA;
    private final PlayerSnapshot playerB;

    public ScoreChangeEvent(Player playerA, Player playerB) {
        this.playerA = PlayerSnapshot.builder()
                .name(playerA.getName())
                .points(playerA.getPoints())
                .advantage(playerA.isAdvantage())
                .build();
        this.playerB = PlayerSnapshot.builder()
                .name(playerB.getName())
                .points(playerB.getPoints())
                .advantage(playerB.isAdvantage())
                .build();
    }
}