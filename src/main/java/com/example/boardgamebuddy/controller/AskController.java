package com.example.boardgamebuddy.controller;


import com.example.boardgamebuddy.service.SpringAiBoardGameServiceModular;
import com.example.boardgamebuddy.service.contract.BoardGameService;
import com.example.boardgamebuddy.domain.entity.Answer;
import com.example.boardgamebuddy.domain.dto.Question;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Answer askQuestion(@RequestBody @Valid Question question) {
        //Em fluxo Stream utilizar application/njson
        return boardGameService.askQuestion(question);
    }

}
