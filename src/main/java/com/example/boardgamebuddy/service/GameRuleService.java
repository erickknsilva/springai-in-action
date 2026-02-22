package com.example.boardgamebuddy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;

@Service
public class GameRuleService {

    private static final Logger logger = LoggerFactory.getLogger(GameRuleService.class);

    public String getRulesForGame(String gameName){

        try{
            var fileName = String.format("classpath:gameRules/%s.txt",
                    gameName.toLowerCase().replace(" ", "_"));
            return new DefaultResourceLoader()
                    .getResource(fileName)
                    .getContentAsString(Charset.defaultCharset());
        }
        catch (IOException ex){
            logger.info("No rules found for game: " + gameName);
            return "";
        }
    }

}
