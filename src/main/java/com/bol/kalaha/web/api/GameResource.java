package com.bol.kalaha.web.api;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath;

import com.bol.kalaha.service.GameService;
import com.bol.kalaha.web.GameMapper;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class GameResource implements KalahGameApi {

    private final GameService gameService;
    private final GameMapper gameMapper;

    @Override
    public ResponseEntity<CreateGameResponse> createGame(CreateJoinGameRequest createGameRequest) {

        final UriComponentsBuilder uriComponentsBuilder = fromCurrentContextPath()
                .path("/api/v1/game/{id}");

        var gameId  = gameService.createGame(gameMapper.mapCreateJoinGame(createGameRequest));
        var uriComponent = uriComponentsBuilder.buildAndExpand(gameId);
        return created(uriComponent.toUri()).body(new CreateGameResponse().id(gameId.toString()));

    }

    @Override
    public ResponseEntity<GameBoard> getGame(UUID gameId) {
        return ok(gameMapper.mapResult(gameService.getGameResult(gameId)));
    }

    @Override
    public ResponseEntity<Void> joinGame(UUID gameId, CreateJoinGameRequest joinGame) {
        gameService.addPlayer(gameId, gameMapper.mapCreateJoinGame(joinGame));
        return ok().build();
    }

    @Override
    public ResponseEntity<GameBoard> play(UUID gameId, Move move) {
        var game = gameService.play(gameId, gameMapper.mapMove(move));
        var result = gameMapper.mapResult(game);
        return ok(result);
    }
}
