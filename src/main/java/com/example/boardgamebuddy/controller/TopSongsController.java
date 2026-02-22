package com.example.boardgamebuddy.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TopSongsController {

    @Value("classpath:/top-songs-prompt/top-songs-prompt.st")
    private Resource topSongPromptTemplate;

    private final ChatClient chatClient;

    public TopSongsController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping(path = "/topSongs", produces = "application/json")
    public List<String> topSongs(@RequestParam("year") String year) {

        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(topSongPromptTemplate)
                        .param("year", year))
                .call()
                .entity(new ParameterizedTypeReference<List<String>>() {
                });
    }

}
