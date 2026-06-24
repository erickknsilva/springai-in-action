package com.example.boardgamebuddy.service.contract;

import com.example.boardgamebuddy.domain.entity.Answer;
import com.example.boardgamebuddy.domain.dto.Question;

public interface BoardGameService {

    Answer askQuestion(Question question, String conversationId);
}
