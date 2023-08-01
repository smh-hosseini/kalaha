package com.bol.kalaha.service;

import com.bol.kalaha.config.AppOptions;
import com.bol.kalaha.domain.Game;
import com.bol.kalaha.domain.GameResult;
import com.bol.kalaha.domain.Move;
import com.bol.kalaha.exception.GameNotFountException;
import com.bol.kalaha.repository.GameRepository;
import com.bol.kalaha.web.dto.CreateJoinGameDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.bol.kalaha.web.validator.ValidatorUtils.validateCreateJoinGameDto;
import static com.bol.kalaha.web.validator.ValidatorUtils.validateMove;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    private final AppOptions appOptions;

    @Override
    public UUID createGame(CreateJoinGameDto dto) {
        validateCreateJoinGameDto(dto);
        var game = Game.create(dto.getPlayerName(), appOptions.getPitNumsPerPlayer(), appOptions.getPitSize());
        gameRepository.saveGame(game);
        return game.getId();
    }

    @Override
    public void addPlayer(UUID id, CreateJoinGameDto dto) {
        validateCreateJoinGameDto(dto);
        getGame(id).addPlayer(dto.getPlayerName());
    }

    @Override
    public GameResult getGameResult(UUID id) {
        return gameRepository.getGame(id).orElseThrow(()
        -> new GameNotFountException("Game not found for id: " + id)).toResult();
    }

    @Override
    public GameResult play(UUID gameId, Move move) {
        validateMove(move);
        return getGame(gameId).move(move.player(), move.pitNum());
    }

    private Game getGame(UUID id) {
        return gameRepository.getGame(id).orElseThrow(()
            -> new GameNotFountException("Game not found for id: " + id));
    }

}
