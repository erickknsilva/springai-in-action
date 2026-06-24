package com.example.boardgamebuddy.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.preretrieval.query.expansion.MultiQueryExpander;
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;
import org.springframework.ai.rag.preretrieval.query.transformation.TranslationQueryTransformer;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.spring.LogbookClientHttpRequestInterceptor;

@Configuration
public class ChatClientConfig {


//    @Bean
//    public ChatClient chatClientModular(ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {
//        var advisor = RetrievalAugmentationAdvisor.builder()
//                .documentRetriever(
//                        VectorStoreDocumentRetriever.builder()
//                                .vectorStore(vectorStore)
//                                .build()
//                ).queryTransformers(TranslationQueryTransformer.builder()
//                        .chatClientBuilder(chatClientBuilder)
//                        .targetLanguage("English")
//                        .build(), RewriteQueryTransformer.builder()
//                        .chatClientBuilder(chatClientBuilder).build())
//                .queryExpander(MultiQueryExpander.builder()
//                        .chatClientBuilder(chatClientBuilder)
//                        .numberOfQueries(4)

    ////                        .includeOriginal(false)
//                        .build())
//                .build();
//
//        return chatClientBuilder
//                .defaultAdvisors(advisor)
//                .build();
//    }

    ChatMemory chatMemory(ChatMemoryRepository chatMemoryRepository) {
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemoryRepository)
                .maxMessages(50)
                .build();
    }

    //Ativando o historico de conversa com LLM  - Historico de mensagens
    //Adicionando memória de chat orientada a mensagens como advisor padrão
//    Essa config adiciona o histrocio de conversação orientada a mensagens, ou seja,
//    cada mensagem é adicionada a um array de mensagens, onde cada mensagem tem um remetente e um conteúdo,
//    e o LLM pode acessar esse array para entender o contexto da conversa.
    @Bean
    public ChatClient chatClientMessageChatMemoryAdvisor(ChatClient.Builder chatClientBuilder, VectorStore vectorStore, ChatMemory chatMemory) {

        return chatClientBuilder
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        QuestionAnswerAdvisor.builder(vectorStore)
                                .searchRequest(SearchRequest.builder().build()).build()

                ).build();
    }

//    Esse joga todos historico de conversação em um String unico, toda  conversa é adicionado a um TEXTO = STRING
//    @Bean
//    ChatClient chatClientPromptChatMemoryAdvisor(ChatClient.Builder chatClientBuilder, VectorStore vectorStore, ChatMemory chatMemory) {
//        return chatClientBuilder
//                .defaultAdvisors(
//                        PromptChatMemoryAdvisor.builder(chatMemory).build(),
//                        QuestionAnswerAdvisor.builder(vectorStore)
//                                .searchRequest(SearchRequest.builder().build()).build())
//                .build();
//    }

    public RestClientCustomizer logBookCustomizer(LogbookClientHttpRequestInterceptor interceptor) {
        return restTemplateBuilder -> restTemplateBuilder.requestInterceptor(interceptor);
    }

}
