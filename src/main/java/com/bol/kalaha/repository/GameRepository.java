package com.bol.kalaha.repository;

import com.bol.kalaha.domain.Game;

import java.util.Optional;
import java.util.UUID;

public interface GameRepository {

    Optional<Game> getGame(UUID id);
    void saveGame(Game game);
}
