package com.bol.kalaha.domain;

import static com.bol.kalaha.domain.PlayerNum.ONE;
import static com.bol.kalaha.domain.PlayerNum.TWO;

import com.bol.kalaha.exception.GameIsFullException;
import lombok.Data;
import java.util.*;
import java.util.stream.IntStream;

@Data
public class Board {

    private Map<PlayerNum, Player> players = new HashMap<>();
    private Map<PlayerNum, Map<Integer, Pit>> houseMap = new HashMap<>();
    private Map<PlayerNum,Pit> stores = new HashMap<>();
    private int numOfPits;
    private int numOfSeeds;
    public Board(int numOfPits, int numOfSeeds) {
        this.numOfPits = numOfPits;
        this.numOfSeeds = numOfSeeds;
        addHouses();
        setOppositePits();
        bindStoresToHouses();
    }

    public void addHouses() {
        Arrays.stream(PlayerNum.values()).forEach(label ->
        {
            var map = houseMap.getOrDefault(label, new HashMap<>());
            IntStream.range(1, numOfPits + 1).forEach(idx -> {
                Pit house = Pit.createHouse(label, numOfSeeds, idx);
                if (idx > 1) {
                    map.getOrDefault(idx - 1, null).setNext(house);
                }
                map.put(idx, house);
                if (idx == 6) {
                    var store = Pit.createStore(label);
                    house.setNext(store);
                    stores.put(label, store);
                }
            });
            houseMap.put(label, map);
        });
    }

    public void addPlayer(String playerName) {
        neededPlayer().ifPresentOrElse(playerLabel -> {
            var player = new Player(playerLabel, playerName, houseMap.get(playerLabel), stores.get(playerLabel));
            players.put(playerLabel, player);
            }, () -> {
                throw new GameIsFullException("You cannot join this game since. Two players are already joined.");
        });

    }

    private void setOppositePits() {
        IntStream.range(1, numOfPits + 1).forEach(index -> {
            var playerOnePit = houseMap.get(ONE).get(index);
            var playerTwoPit = houseMap.get(TWO).get(numOfPits-(index-1));
            playerTwoPit.setOpposite(playerOnePit);
            playerOnePit.setOpposite(playerTwoPit);
        });

    }

    private void bindStoresToHouses() {
        houseMap.get(ONE).get(numOfPits).next().setNext(houseMap.get(TWO).get(1));
        houseMap.get(TWO).get(numOfPits).next().setNext(houseMap.get(ONE).get(1));
    }

    private Optional<PlayerNum> neededPlayer() {

        if (players.get(ONE) == null) {
            return Optional.of(ONE);
        } else if (players.get(TWO) == null) {
            return Optional.of(TWO);
        }
        return Optional.empty();
    }

    public Map<PlayerNum, Pit> getStores() {
        return stores;
    }

}
