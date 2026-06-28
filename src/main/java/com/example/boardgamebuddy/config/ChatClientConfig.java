package com.example.boardgamebuddy.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.memory.repository.jdbc.PostgresChatMemoryRepositoryDialect;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.spring.LogbookClientHttpRequestInterceptor;

import javax.sql.DataSource;

@Configuration
public class ChatClientConfig {


    /**metodo de referencia
     *
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
                        .numberOfQueries(4)
                       includeOriginal(false)
                        .build())
                .build();

        return chatClientBuilder
                .defaultAdvisors(advisor)
                .build();
    }
    **/

    @Bean
    ChatMemory chatMemory(ChatMemoryRepository chatMemoryRepository) {
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemoryRepository)
                .maxMessages(50)
                .build();
    }


    @Bean
    public ChatClient chatClientMessageChatMemoryAdvisor(ChatClient.Builder chatClientBuilder, VectorStore vectorStore, ChatMemory chatMemory) {

        return chatClientBuilder
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        QuestionAnswerAdvisor.builder(vectorStore)
                                .searchRequest(SearchRequest.builder().build()).build()

                ).build();
    }

    @Bean
    ChatMemoryRepository chatMemoryRepository(DataSource datasource){

        return JdbcChatMemoryRepository.builder()
                .dialect(new PostgresChatMemoryRepositoryDialect())
                .dataSource(datasource)
                .build();
    }

    public RestClientCustomizer logBookCustomizer(LogbookClientHttpRequestInterceptor interceptor) {
        return restTemplateBuilder -> restTemplateBuilder.requestInterceptor(interceptor);
    }

}
