package com.example.boardgamebuddy.contracts;

import com.example.boardgamebuddy.domain.Answer;
import com.example.boardgamebuddy.domain.Question;

public interface BoardGameService {

    Answer askQuestion(Question question);
}
