package com.bol.kalaha.service;

import com.bol.kalaha.domain.GameResult;
import com.bol.kalaha.domain.Move;
import com.bol.kalaha.web.dto.CreateJoinGameDto;
import java.util.UUID;

public interface GameService {

    UUID createGame(CreateJoinGameDto dto);
    void addPlayer(UUID gameId, CreateJoinGameDto dto);
    GameResult getGameResult(UUID gameId);
    GameResult play(UUID gameId, Move move);

}
