package com.example.tennisscore.infrastructure.adapter.input.rest;

import com.example.tennisscore.application.port.input.TennisGamePort;
import com.example.tennisscore.infrastructure.adapter.input.rest.data.GameRequest;
import com.example.tennisscore.infrastructure.adapter.input.rest.data.GameResponse;
import com.example.tennisscore.infrastructure.adapter.input.rest.mappers.GameMapper;
import com.example.tennisscore.infrastructure.adapter.input.rest.validator.InputValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.tennisscore.infrastructure.adapter.input.rest.GameApiPaths.GAME_BASE_PATH;
import static com.example.tennisscore.infrastructure.adapter.input.rest.GameApiPaths.PLAY_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(value = GAME_BASE_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class GameController {

    private final TennisGamePort commandPort;
    private final GameMapper gameMapper;

    @PostMapping(PLAY_PATH)
    public ResponseEntity<GameResponse> play(@RequestBody GameRequest gameRequest) {
        InputValidator.validateInput(gameRequest);
        return ResponseEntity.ok(gameMapper.map(commandPort.play(gameMapper.map(gameRequest))));
    }

}
