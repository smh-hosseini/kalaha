package com.bol.kalaha.domain;

import com.bol.kalaha.exception.WrongMoveException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    public void testPlay_ValidHouseNum() {
        Map<Integer, Pit> houses = new HashMap<>();
        var pit = Pit.createHouse(PlayerNum.ONE, 1, 1);
        var store = Pit.createStore(PlayerNum.ONE);
        pit.setNext(store);

        houses.put(1, pit);
        var player = new Player(PlayerNum.ONE, "Player 1", houses, store);

        var nextPlayer = player.play(1);

        assertEquals(nextPlayer, player.label());
    }

    @Test
    public void testPlay_EmptyHouse() {
        // Arrange
        Map<Integer, Pit> houses = new HashMap<>();
        houses.put(1, Pit.createHouse(PlayerNum.ONE, 0, 1));
        Player player = new Player(PlayerNum.ONE, "Player 1", houses, Pit.createStore(PlayerNum.ONE));

        // Act & Assert (expecting WrongMoveException with "House must have seeds to take turn" message)
        assertThrows(WrongMoveException.class, () -> player.play(1));
    }

    @Test
    public void testAllPitsEmpty_AllEmpty() {
        // Arrange
        Map<Integer, Pit> houses = new HashMap<>();
        houses.put(1, Pit.createHouse(PlayerNum.ONE, 0, 1));
        houses.put(2, Pit.createHouse(PlayerNum.ONE, 0, 2));
        Pit store = Pit.createStore(PlayerNum.ONE);
        Player player = new Player(PlayerNum.ONE, "Player 1", houses, store);

        // Act & Assert
        assertTrue(player.allPitsEmpty());
    }

    @Test
    public void testAllPitsEmpty_NotAllEmpty() {
        // Arrange
        Map<Integer, Pit> houses = new HashMap<>();
        houses.put(1, Pit.createHouse(PlayerNum.ONE, 0, 1));
        houses.get(1).addSeed();
        houses.put(2, Pit.createHouse(PlayerNum.ONE, 0, 2));
        Pit store = Pit.createStore(PlayerNum.ONE);
        Player player = new Player(PlayerNum.ONE, "Player 1", houses, store);

        // Act & Assert
        assertFalse(player.allPitsEmpty());
    }

    @Test
    public void testScore() {
        // Arrange
        Map<Integer, Pit> houses = new HashMap<>();
        houses.put(1, Pit.createHouse(PlayerNum.ONE, 0, 1));
        houses.get(1).addSeed();
        Pit store = Pit.createStore(PlayerNum.ONE);
        store.addSeed();
        Player player = new Player(PlayerNum.ONE, "Player 1", houses, store);

        // Act & Assert
        assertEquals(1, player.score());
    }

}