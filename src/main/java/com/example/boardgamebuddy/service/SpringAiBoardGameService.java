package com.example.boardgamebuddy.service;

import com.example.boardgamebuddy.contracts.BoardGameService;
import com.example.boardgamebuddy.domain.Answer;
import com.example.boardgamebuddy.domain.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class SpringAiBoardGameService implements BoardGameService {

    private final ChatClient chatClient;
    private final GameRuleService gameRuleService;
    private static final Logger logger = LoggerFactory.getLogger(SpringAiBoardGameService.class);

    @Value("classpath:/promptTemplates/systemPromptTemplate.st")
    Resource promptTemplate;

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

    public SpringAiBoardGameService(ChatClient chatClient, GameRuleService gameRuleService) {
        this.chatClient = chatClient;
        this.gameRuleService = gameRuleService;
    }

    @Override
    public Answer askQuestion(Question question) {

        var gameRules = gameRuleService.getRulesForGame(question.gameTitle());

        var responseEntity = chatClient.prompt()
                .system(systemSpec -> systemSpec
                        .text(promptTemplate)
                        .param("gameTitle", question.gameTitle())
                        .param("question", question.question())
                        .param("rules", gameRules)
                )
                .user(question.question())
                .call()
                .responseEntity(Answer.class);

        var response = responseEntity.response();
        logUsage(response.getMetadata().getUsage());

        return responseEntity.entity();
    }

    private void logUsage(Usage usage) {
        logger.info("Token usage: prompt={}, generation={}, total={}",
                usage.getPromptTokens(),
                usage.getCompletionTokens(),
                usage.getTotalTokens());
    }

    /*
    @Deprecated
    @Override
    public Flux<String> askQuestion(Question question) {

        var gameRules = gameRuleService.getRulesForGame(question.gameTitle());

        return chatClient.prompt()
                .system(systemSpec -> systemSpec
                        .text(promptTemplate)
                        .param("gameTitle", question.gameTitle())
                        .param("question", question.question())
                        .param("rules", gameRules)
                )
                .user(question.question())
                .stream()
                .content();

    }
    */

}
