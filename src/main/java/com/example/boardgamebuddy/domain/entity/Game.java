package com.example.boardgamebuddy.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

public record Game(
        @Id
        Long id,
        String slug,
        String title,
        float complexity
) {

        public GameComplexity complexityEnum() {
                int rounded = Math.round(complexity);
                return GameComplexity.values()[rounded];
        }
}
