package com.example.boardgamebuddy.service;

import com.example.boardgamebuddy.domain.Answer;
import com.example.boardgamebuddy.domain.AnswerTwo;
import com.example.boardgamebuddy.domain.Question;
import com.example.boardgamebuddy.exception.AnswerNotRelevantException;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.evaluation.RelevancyEvaluator;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

public class SelfEvaluatingBoardGameService {

    private final ChatClient chatClient;
    private final RelevancyEvaluator evaluator;

    public SelfEvaluatingBoardGameService(ChatClient.Builder chatClientBuilder) {

        var chatOptions = ChatOptions.builder()
                .model("gpt-4o-mini")
                .build();

        this.chatClient = chatClientBuilder
                .defaultOptions(chatOptions).build();

        this.evaluator = new RelevancyEvaluator(chatClientBuilder);

    }

    @Retryable(retryFor = AnswerNotRelevantException.class, maxAttempts = 2)
    public AnswerTwo askQuestion(Question question) {

        var answerText = chatClient.prompt()
                .user(question.question())
                .call()
                .content();

        evaluateRelevancy(question,answerText);

        return new AnswerTwo(question.gameTitle(), answerText);
    }

    @Recover
    public AnswerTwo recover(AnswerNotRelevantException e) {
        return new AnswerTwo("","I'm sorry, I wasn't able to answer the question.");
    }

    private void evaluateRelevancy(Question question, String answerText) {

        var evaluationRequest = new EvaluationRequest(question.question(),answerText);

        var evaluationResponse = evaluator.evaluate(evaluationRequest);

        if(!evaluationResponse.isPass()){
            throw new AnswerNotRelevantException(question.question(), answerText);
        }

    }

}
