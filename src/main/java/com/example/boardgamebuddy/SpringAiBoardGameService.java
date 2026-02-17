package com.example.boardgamebuddy;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class SpringAiBoardGameService implements BoardGameService{

    private final ChatClient chatClient;

    public SpringAiBoardGameService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public Anwser askQuestion(Question question) {

        var anwserText = chatClient.prompt()
                .user(question.question())
                .call()
                .content();
        return new Anwser(anwserText);
    }
}
