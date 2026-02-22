package com.example.boardgamebuddy.config;

import org.springframework.ai.chat.client.ChatClient;
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


    public RestClientCustomizer logBookCustomizer(LogbookClientHttpRequestInterceptor interceptor){
        return restTemplateBuilder -> restTemplateBuilder.requestInterceptor(interceptor);
    }

}
