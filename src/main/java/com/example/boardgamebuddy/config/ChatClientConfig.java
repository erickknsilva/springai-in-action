package com.example.boardgamebuddy.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.preretrieval.query.expansion.MultiQueryExpander;
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;
import org.springframework.ai.rag.preretrieval.query.transformation.TranslationQueryTransformer;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.spring.LogbookClientHttpRequestInterceptor;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder.build();
    }

    @Bean
    public ChatClient chatClientConsultor(ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {
            return  chatClientBuilder.defaultAdvisors(QuestionAnswerAdvisor.builder(vectorStore)
                    .build())
                    .build();
    }

    @Bean
    public ChatClient chatClientModular(ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {
        var advisor = RetrievalAugmentationAdvisor.builder()
                .documentRetriever(
                        VectorStoreDocumentRetriever.builder()
                                .vectorStore(vectorStore)
                                .build()
                ).queryTransformers(TranslationQueryTransformer.builder()
                        .chatClientBuilder(chatClientBuilder)
                        .targetLanguage("English")
                        .build(), RewriteQueryTransformer.builder()
                        .chatClientBuilder(chatClientBuilder).build())
                .queryExpander(MultiQueryExpander.builder()
                        .chatClientBuilder(chatClientBuilder)
                        .build())
                .build();

        return chatClientBuilder
                .defaultAdvisors(advisor)
                .build();
    }

    public RestClientCustomizer logBookCustomizer(LogbookClientHttpRequestInterceptor interceptor){
        return restTemplateBuilder -> restTemplateBuilder.requestInterceptor(interceptor);
    }

}
