package com.bol.kalaha.domain;

import com.bol.kalaha.exception.GameNotStartedException;
import com.bol.kalaha.exception.WrongMoveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game game;

    @BeforeEach
    public void setUp() {
        int numOfPits = 6;
        int pitSize = 4;
        game = Game.create("Player 1", numOfPits, pitSize);
    }

    @Test
    public void testCreateGame() {
        // Assert
        assertNotNull(game.getId());
        assertNotNull(game.getBoard());
        assertEquals(GameStatus.WAITING_FOR_PLAYER_TWO, game.getStatus());
        assertNotNull(game.getActivePlayer());
    }

    @Test
    public void testAddPlayer() {
        // Arrange
        String playerName = "Player 2";

        // Act
        game.addPlayer(playerName);

        // Assert
        assertNotNull(game.getActivePlayer());
        assertEquals("Player 1", game.getActivePlayer().name());
        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
    }

    @Test
    public void testMove() {
        // Arrange
        int pitNum = 1;
        game.addPlayer("Player 2");

        // Act
        GameResult result = game.move(PlayerNum.ONE, pitNum);

        // Assert
        assertNotNull(result);
        assertEquals(GameStatus.IN_PROGRESS, result.status());
        assertNotNull(result.board());
        assertNotNull(result.turn());
    }

    @Test
    public void testMove_GameNotStartedException() {
        // Arrange
        int pitNum = 1;

        // Act and Assert
        assertThrows(GameNotStartedException.class, () -> game.move(PlayerNum.ONE, pitNum));
    }

    @Test
    public void testMove_WrongMoveException() {
        // Arrange
        game.addPlayer("Player 2");
        int pitNum = 1;

        // Act and Assert
        assertThrows(WrongMoveException.class, () -> game.move(PlayerNum.TWO, pitNum));
    }

    @Test
    public void testToResult() {
        // Act
        GameResult result = game.toResult();

        // Assert
        assertNotNull(result);
        assertEquals(GameStatus.WAITING_FOR_PLAYER_TWO, result.status());
        assertNotNull(result.board());
        assertNotNull(result.turn());
    }


}