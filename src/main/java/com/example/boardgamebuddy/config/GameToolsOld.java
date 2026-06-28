package com.example.boardgamebuddy.config;

import com.example.boardgamebuddy.domain.dto.GameComplexityResponse;
import com.example.boardgamebuddy.domain.entity.Game;
import com.example.boardgamebuddy.domain.entity.GameComplexity;
import com.example.boardgamebuddy.repository.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

//@Component
public class GameToolsOld {

    private final static Logger LOGGER = LoggerFactory.getLogger(GameToolsOld.class);

    private final GameRepository gameRepository;

    public GameToolsOld(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

//    @Tool(name = "getGameComplexity", description = "Returns a game's complexity/dificult " +
//            "given the game's title/name.")
    public GameComplexityResponse getGameComplexity(
            @ToolParam(description = "The title of the game")
            String gameTitle) {

        var gameSlug = gameTitle.toLowerCase().replace(" ", "_");

        LOGGER.info("Getting complexity for {} ({})", gameTitle, gameSlug);

        var gameOpt = gameRepository.findBySlug(gameSlug);

        var game = gameOpt.orElseGet(() -> {
            LOGGER.warn("Game not found: {}", gameSlug);
            return new Game(null, gameSlug, gameTitle, GameComplexity.UNKNOWN.getValue());
        });

        return new GameComplexityResponse(game.title(), game.complexityEnum());
    }


}
