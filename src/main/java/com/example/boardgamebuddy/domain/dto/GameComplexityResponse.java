package com.example.boardgamebuddy.domain.dto;

import com.example.boardgamebuddy.domain.entity.GameComplexity;

public record GameComplexityResponse(
        String gameTitle,
        GameComplexity  gameComplexity
) {
}
