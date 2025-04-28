package com.example.tennisscore.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Player {

    public static final String PLAYER_A = "Player A";
    public static final String PLAYER_B = "Player B";

    private final String name;
    private int points;
    private boolean advantage;


    public void resetAdvantage() {
        this.advantage = false;
    }
}
