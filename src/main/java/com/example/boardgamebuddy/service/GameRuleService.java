package com.example.boardgamebuddy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

@Service
public class GameRuleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameRuleService.class);
    private final VectorStore vectorStore;


    public GameRuleService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }


    public String getRulesForGame(String gameName, String question){

        var searchRequest = SearchRequest.builder()
                .query(question)
                .similarityThreshold(0.5)
//                .topK(6)
                .filterExpression(
                        new FilterExpressionBuilder()
                                .eq("gameTitle", normalizeGameTitle(gameName)).build())
                .build();

        System.err.println("Search request: " + searchRequest);
        LOGGER.info("Search request: {}", searchRequest);

        var similarDocs = vectorStore.similaritySearch(searchRequest);

        if(similarDocs.isEmpty()){
            LOGGER.error("No similar documents found");
            return "The rules for " + gameName  + " are not available";
        }

        return  similarDocs.stream()
                .map(Document::getText)
                .collect(Collectors.joining(System.lineSeparator()));


//            var fileName = String.format("classpath:gameRules/%s.txt",
//                    gameName.toLowerCase().replace(" ", "_"));
//
//            return new DefaultResourceLoader()
//                    .getResource(fileName)
//                    .getContentAsString(Charset.defaultCharset());
    }

    private String normalizeGameTitle(String gameTitle) {
        return gameTitle.toLowerCase().replace(" ", "_");
    }


}
