package com.bol.kalaha.domain;

import com.bol.kalaha.exception.GameNotStartedException;
import com.bol.kalaha.exception.WrongMoveException;
import lombok.Data;
import java.util.UUID;

@Data
public class Game {

    private UUID id;

    private Board board;

    private Player activePlayer;

    private GameStatus status;

    public static Game create(String playerName, int numOfPits, int pitSize) {
        var board = new Board(numOfPits, pitSize);
        board.addPlayer(playerName);
        Game game = new Game();
        game.id = UUID.randomUUID();
        game.board = board;
        game.status = GameStatus.WAITING_FOR_PLAYER_TWO;
        game.activePlayer = board.getPlayers().get(PlayerNum.ONE);
        return game;
    }

    public GameResult move(PlayerNum label, int pitNum) {
        if (status.equals(GameStatus.WAITING_FOR_PLAYER_TWO)) {
            throw new GameNotStartedException("Game needs one more player to start");
        }
        checkMoveIsPossible(label);
        var playerNum = activePlayer.play(pitNum);
        activePlayer = board.getPlayers().get(playerNum);
        if (weHaveAWinner()) {
            status = gameStatus();
        }
        return new GameResult(board, status, activePlayer);
    }

    public void checkMoveIsPossible(PlayerNum playerNum) {
        if (activePlayer.label() != playerNum) {
            throw new WrongMoveException(String.format("It is not Player %s turn.", playerNum));
        }
    }

    private boolean weHaveAWinner() {
        return board.getPlayers().values().stream().anyMatch(Player::allPitsEmpty);
    }

    public void addPlayer(String name) {
        board.addPlayer(name);
        activePlayer = board.getPlayers().get(PlayerNum.ONE);
        status = GameStatus.IN_PROGRESS;
    }

    public GameResult toResult()
    {
        return new GameResult(board, status, activePlayer);
    }

    private GameStatus gameStatus() {
        board.getPlayers().values().forEach(Player::gameOver);

        int score1 = board.getPlayers().get(PlayerNum.ONE).score();
        int score2 = board.getPlayers().get(PlayerNum.ONE).score();
        if (score1 > score2) {
            return GameStatus.PLAYER_ONE_WIN;
        }
        if (score2 > score1) {
            return GameStatus.PLAYER_TWO_WIN;
        }
        return GameStatus.DRAW;
    }
}
