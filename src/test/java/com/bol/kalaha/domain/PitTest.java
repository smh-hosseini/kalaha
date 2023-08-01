package com.bol.kalaha.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PitTest {

    @Test
    public void testCreateHouse() {
        // Arrange
        int seeds = 4;
        int index = 1;
        PlayerNum owner = PlayerNum.ONE;

        // Act
        Pit house = Pit.createHouse(owner, seeds, index);

        // Assert
        assertNotNull(house);
        assertEquals(seeds, house.count());
        assertEquals(owner, house.getOwner());
        assertEquals(PitType.HOUSE, house.getType());
        assertEquals(index, house.getIndex());
        assertFalse(house.isEmpty());
    }

    @Test
    public void testCreateStore() {
        // Arrange
        PlayerNum owner = PlayerNum.ONE;

        // Act
        Pit store = Pit.createStore(owner);

        // Assert
        assertNotNull(store);
        assertEquals(0, store.count());
        assertEquals(owner, store.getOwner());
        assertEquals(PitType.STORE, store.getType());
        assertEquals(0, store.getIndex());
        assertTrue(store.isEmpty());
    }

    @Test
    public void testAddSeed() {
        // Arrange
        int initialSeeds = 0;
        Pit pit = new Pit(PlayerNum.ONE, initialSeeds, PitType.HOUSE, 1);

        // Act
        pit.addSeed();

        // Assert
        assertEquals(initialSeeds + 1, pit.count());
        assertFalse(pit.isEmpty());
    }

    @Test
    public void testTakeAllSeeds_House() {
        // Arrange
        int initialSeeds = 5;
        Pit pit = new Pit(PlayerNum.ONE, initialSeeds, PitType.HOUSE, 1);

        // Act
        int seedsTaken = pit.takeAllSeeds();

        // Assert
        assertEquals(initialSeeds, seedsTaken);
        assertEquals(0, pit.count());
        assertTrue(pit.isEmpty());
    }

    @Test
    public void testTakeAllSeeds_Store() {
        // Arrange
        int initialSeeds = 5;
        Pit pit = new Pit(PlayerNum.ONE, initialSeeds, PitType.STORE, 0);

        // Act
        int seedsTaken = pit.takeAllSeeds();

        // Assert (Store should not lose seeds)
        assertEquals(0, seedsTaken);
        assertEquals(initialSeeds, pit.count());
        assertFalse(pit.isEmpty());
    }

}