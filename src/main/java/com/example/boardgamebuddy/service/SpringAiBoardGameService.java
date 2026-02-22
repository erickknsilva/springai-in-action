package com.example.boardgamebuddy;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class SpringAiBoardGameService implements BoardGameService{

    private final ChatClient chatClient;

    @Value("classpath:/promptTemplates/questionPromptTemplate.st")
    Resource questionPromptTemplate;

    /*  prompt template
    private static final String questionPromptTemplate = """
      Answer this question about {gameTitle}: {question}
      """;
    **/
    /*
    private static final String questionPromptTemplate = """
    You are a helpful assistant, answering questions about tabletop games.
    If you don't know anything about the game or don't know the answer,
    say "I don't know".

    The game is {gameTitle}.

    The question is: {question}.
    """;
    */
    
    public SpringAiBoardGameService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public Answer askQuestion(Question question) {

//        String prompt = "Answer this question about " + question.gameTitle() +
//                ":" + question.question();

        var anwserText = chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(questionPromptTemplate)
                        .param("gameTitle", question.gameTitle())
                        .param("question",question.question())
                )
                .call()
                .content();

        return new Answer(question.gameTitle(), anwserText);
    }

}
