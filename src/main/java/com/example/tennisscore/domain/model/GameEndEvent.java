package com.example.tennisscore.domain.model;

import lombok.Getter;

@Getter
public class GameEndEvent implements ScoreEvent {
    private final PlayerSnapshot winner;

    public GameEndEvent(Player player) {
        this.winner = PlayerSnapshot.builder()
                .name(player.getName())
                .points(player.getPoints())
                .advantage(player.isAdvantage())
                .build();
    }
}