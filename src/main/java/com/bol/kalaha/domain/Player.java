package com.bol.kalaha.domain;

import com.bol.kalaha.exception.WrongMoveException;
import java.util.Map;

public record Player (PlayerNum label, String name, Map<Integer, Pit> houses, Pit store) {


    public PlayerNum play(int houseNum) {
        var house = getHouse(houseNum);
        checkHouseSeeds(house);
        return moveSeeds(house);
    }

    public boolean allPitsEmpty() {
        return houses.values().stream().allMatch(Pit::isEmpty);
    }

    public int score() {
        return store().count();
    }

    private void checkHouseSeeds(Pit house) {
        if (house.isEmpty()) {
            throw new WrongMoveException("House must have seeds to take turn");
        }
    }

    private PlayerNum moveSeeds(Pit house) {
        Integer seeds = house.takeAllSeeds();
        Pit pit = house;
        while (seeds > 0) {
            pit = pit.next();
            if (pit.isStore() && !pit.isOwnerStore(label)) {
                pit = pit.next();
            }
            seeds--;
            pit.addSeed();
        }
        if (shouldCapture(pit)) {
            store().addSeeds(pit.getOppositePit().capture());
            store().addSeeds(pit.capture());
        }
        return pit.getOwner();
    }

    private Pit getHouse(int houseNum) {
        houses.computeIfAbsent(houseNum, key -> {
            throw new WrongMoveException("House label must be between 1 and " + houses.size());
        });
        return this.houses.get(houseNum);
    }

    private boolean shouldCapture(Pit pit) {
        return !pit.isStore() &&
                pit.count() == 1
                && pit.getOwner().equals(this)
                && !pit.getOppositePit().isEmpty();
    }


    public void gameOver() {
        for (var house : houses.values()) {
            store().addSeeds(house.takeAllSeeds());
        }
    }

}
