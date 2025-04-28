package com.example.tennisscore.application.port.input;

import com.example.tennisscore.domain.model.GameInput;
import com.example.tennisscore.domain.model.GameOutput;

public interface TennisGamePort {
    GameOutput play(GameInput input);
}
