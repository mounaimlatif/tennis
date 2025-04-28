package com.example.tennisscore.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PlayerSnapshot {
    String name;
    int points;
    boolean advantage;
}