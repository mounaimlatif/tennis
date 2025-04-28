package com.example.tennisscore.infrastructure.adapter.input.rest.mappers;

import com.example.tennisscore.domain.model.GameInput;
import com.example.tennisscore.domain.model.GameOutput;
import com.example.tennisscore.infrastructure.adapter.input.rest.data.GameRequest;
import com.example.tennisscore.infrastructure.adapter.input.rest.data.GameResponse;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {

    public GameInput map(GameRequest gameRequest) {
        return GameInput.builder().input(gameRequest.input()).build();
    }

    public GameResponse map(GameOutput gameOutput) {
        return new GameResponse(gameOutput.getOutput());
    }
}
