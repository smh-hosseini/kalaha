package com.bol.kalaha.domain;

import com.bol.kalaha.exception.GameIsFullException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;

    @BeforeEach
    public void setUp() {
        int numOfPits = 6;
        int numOfSeeds = 4;
        board = new Board(numOfPits, numOfSeeds);
    }

    @Test
    public void testAddPlayer() {
        // Arrange
        String playerName = "Player 1";

        // Act
        board.addPlayer(playerName);

        // Assert
        assertTrue(board.getPlayers().containsKey(PlayerNum.ONE));
        assertNotNull(board.getPlayers().get(PlayerNum.ONE));
        assertEquals(playerName, board.getPlayers().get(PlayerNum.ONE).name());
    }

    @Test
    public void testAddTwoPlayers() {
        // Arrange
        String playerName1 = "Player 1";
        String playerName2 = "Player 2";

        // Act
        board.addPlayer(playerName1);
        board.addPlayer(playerName2);

        // Assert
        assertTrue(board.getPlayers().containsKey(PlayerNum.ONE));
        assertTrue(board.getPlayers().containsKey(PlayerNum.TWO));
        assertNotNull(board.getPlayers().get(PlayerNum.ONE));
        assertNotNull(board.getPlayers().get(PlayerNum.TWO));
        assertEquals(playerName1, board.getPlayers().get(PlayerNum.ONE).name());
        assertEquals(playerName2, board.getPlayers().get(PlayerNum.TWO).name());
    }

    @Test
    public void testAddPlayer_GameIsFullException() {
        // Arrange
        String playerName1 = "Player 1";
        String playerName2 = "Player 2";
        String playerName3 = "Player 3";

        // Act
        board.addPlayer(playerName1);
        board.addPlayer(playerName2);

        // Assert
        assertThrows(GameIsFullException.class, () -> board.addPlayer(playerName3));
    }

    @Test
    public void testAddHouses() {
        // Act
        board.addHouses();

        // Assert
        assertEquals(6, board.getHouseMap().get(PlayerNum.ONE).size());
        assertEquals(6, board.getHouseMap().get(PlayerNum.TWO).size());
    }

    @Test
    public void testGetStores() {
        // Act
        Map<PlayerNum, Pit> stores = board.getStores();

        // Assert
        assertNotNull(stores);
        assertTrue(stores.containsKey(PlayerNum.ONE));
        assertTrue(stores.containsKey(PlayerNum.TWO));
        assertNotNull(stores.get(PlayerNum.ONE));
        assertNotNull(stores.get(PlayerNum.TWO));
    }

    @Test
    public void testBindStoresToHouses() {
        // Act

        // Assert
        Pit store1 = board.getStores().get(PlayerNum.ONE);
        Pit store2 = board.getStores().get(PlayerNum.TWO);
        Pit pit6Player1 = board.getHouseMap().get(PlayerNum.ONE).get(5);
        Pit pit1Player2 = board.getHouseMap().get(PlayerNum.TWO).get(5);
        assertEquals(store1, pit6Player1.next().next());
        assertEquals(store2, pit1Player2.next().next());
    }
}