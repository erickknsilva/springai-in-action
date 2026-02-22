package com.example.boardgamebuddy.controller;


import com.example.boardgamebuddy.domain.Answer;
import com.example.boardgamebuddy.domain.Question;
import com.example.boardgamebuddy.contracts.BoardGameService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
public class AskController {

    private final BoardGameService boardGameService;

    public AskController(BoardGameService boardGameService) {
        this.boardGameService = boardGameService;
    }

    @PostMapping(path = "/ask", produces = "application/json")
    public Answer askQuestion(@RequestBody @Valid Question question) {
        return boardGameService.askQuestion(question);
    }

}
