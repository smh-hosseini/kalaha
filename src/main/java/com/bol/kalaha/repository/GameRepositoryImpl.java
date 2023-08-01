package com.bol.kalaha.repository;

import com.bol.kalaha.domain.Game;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class GameRepositoryImpl implements GameRepository {

    private static final Map<UUID, Game> gameMap = new HashMap<>();
    @Override
    public Optional<Game> getGame(UUID id) {
        return Optional.ofNullable(gameMap.get(id));
    }

    @Override
    public void saveGame(Game game)
    {
        gameMap.put(game.getId(), game);
    }
}
