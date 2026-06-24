package com.example.boardgamebuddy.controller;


import com.example.boardgamebuddy.service.SpringAiBoardGameServiceModular;
import com.example.boardgamebuddy.service.contract.BoardGameService;
import com.example.boardgamebuddy.domain.entity.Answer;
import com.example.boardgamebuddy.domain.dto.Question;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RequestMapping
@RestController
public class AskController {

    private final BoardGameService boardGameService;
    private final SpringAiBoardGameServiceModular boardGameServiceModular;

    public AskController(BoardGameService boardGameService, SpringAiBoardGameServiceModular boardGameServiceModular) {
        this.boardGameService = boardGameService;
        this.boardGameServiceModular = boardGameServiceModular;
    }

    @PostMapping(path = "/ask", produces = "application/json")
    public Answer askQuestion(
            @RequestHeader(name="X_AI_CONVERSATION_ID", defaultValue = "default")String conversationId,
            @RequestBody @Valid Question question) {
        //Em fluxo Stream utilizar application/njson
        return boardGameServiceModular.askQuestion(question, conversationId);
    }

}
