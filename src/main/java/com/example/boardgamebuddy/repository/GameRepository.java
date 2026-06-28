package com.example.boardgamebuddy.repository;

import com.example.boardgamebuddy.domain.entity.Game;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GameRepository
        extends CrudRepository<Game, Long> {


    Optional<Game> findBySlug(String gameName);

}
