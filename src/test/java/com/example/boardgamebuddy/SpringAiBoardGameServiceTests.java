package com.example.boardgamebuddy;

import com.example.boardgamebuddy.domain.Answer;
import com.example.boardgamebuddy.domain.Question;
import com.example.boardgamebuddy.contracts.BoardGameService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.evaluation.FactCheckingEvaluator;
import org.springframework.ai.chat.evaluation.RelevancyEvaluator;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringAiBoardGameServiceTests {

    //Teste responsavel por avaliar a relevancia da resposta retornada pela LLM
    //

    @Autowired
    private BoardGameService boardGameService;

    @Autowired
    private ChatClient.Builder chatClientBuilder;

    private FactCheckingEvaluator factCheckingEvaluator;

    private RelevancyEvaluator relevancyEvaluator;

    @BeforeEach
    public void setup(){
        this.relevancyEvaluator = new RelevancyEvaluator(chatClientBuilder);
        this.factCheckingEvaluator = FactCheckingEvaluator.builder(chatClientBuilder).build();
    }

    @Test
    public void evaluateRelevancy(){
        String userText = "Porquê o ceú é azul ?";

        Question question = new Question(null,userText);
        Answer anwser = boardGameService.askQuestion(question);

        EvaluationRequest evaluationRequest = new EvaluationRequest(userText,anwser.answer());

        EvaluationResponse response = relevancyEvaluator.evaluate(evaluationRequest);

        Assertions.assertThat(response.isPass())
                .withFailMessage("""
                        ========================================
                                  The answer "%s"
                                  is not considered relevant to the question
                                  "%s".
                                  ========================================
                        """, anwser.answer(), userText).isTrue();

    }

    @Test
    public void evaluateFactualAccuracy(){
        //arrange
        String userText = "Why is the sky blue?";
        var question = new Question(null, userText);
        var answer =  boardGameService.askQuestion(question);

        //act
        EvaluationRequest evaluationRequest = new EvaluationRequest(userText,answer.answer());
        var response = factCheckingEvaluator.evaluate(evaluationRequest);

        //assert
        Assertions.assertThat(response.isPass())
                .withFailMessage("""
          ========================================
          The answer "%s"
          is not considered correct for the question
          "%s".
          ========================================
          """, answer.answer(), userText).isTrue();

    }




}
