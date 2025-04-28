package com.example.tennisscore.application.port.output;

import com.example.tennisscore.domain.service.ScoreContext;

public interface DisplayScorePort {
    String result(ScoreContext context);
}