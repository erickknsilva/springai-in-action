package com.example.boardgamebuddy.service;

import com.example.boardgamebuddy.service.contract.BoardGameService;
import com.example.boardgamebuddy.domain.entity.Answer;
import com.example.boardgamebuddy.domain.dto.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import static org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor.FILTER_EXPRESSION;

@Service
public class SpringAiBoardGameServiceConsultor implements BoardGameService {

    private static final Logger logger = LoggerFactory.getLogger(SpringAiBoardGameServiceConsultor.class);

    private final ChatClient chatClient;
    private final GameRuleService gameRuleService;

    @Value("classpath:/promptTemplates/systemPromptTemplate.st")
    private Resource promptTemplate;

    @Value("file://${HOME}/documents/Documento sem título.pdf")
    private Resource documentResource;

    public SpringAiBoardGameServiceConsultor(ChatClient chatClient, GameRuleService gameRuleService) {
        this.chatClient = chatClient;
        this.gameRuleService = gameRuleService;
    }

    @Override
    public Answer askQuestion(Question question) {

        String gameNameMatch = String.format("gameTitle == '%s'", question.gameTitle());

        return chatClient.prompt()
                .system(systemSpec -> systemSpec
                        .text(promptTemplate)
                        .param("gameTitle", question.gameTitle())
                )
                .user(question.question())
                .advisors(advisorSpec -> advisorSpec
                        .param(FILTER_EXPRESSION, gameNameMatch))
                .call()
                .entity(Answer.class);

    }

    private void logUsage(Usage usage) {
        logger.info("Token usage: prompt={}, generation={}, total={}",
                usage.getPromptTokens(),
                usage.getCompletionTokens(),
                usage.getTotalTokens());
    }

    public void loadDocument(VectorStore vectorStore) {
        DocumentReader reader = new TextReader(documentResource);
        TextSplitter splitter = TokenTextSplitter.builder().build();
        vectorStore.accept(splitter.apply(reader.get()));

    }

}
