package com.example.boardgamebuddy.domain.entity;

public enum GameComplexity {


    UNKNOWN(0),
    EASY(1),
    MODERATE_EASY(2),
    MODERATE(3),
    MODERATELY_DIFFICULT(4),
    DIFFICULT(5);

    private final int value;

    GameComplexity(int values) {
        this.value = values;
    }

    public int getValue() {
        return value;
    }

}
