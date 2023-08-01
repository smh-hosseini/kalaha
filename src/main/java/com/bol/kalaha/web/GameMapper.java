package com.bol.kalaha.web;

import com.bol.kalaha.domain.PlayerNum;
import com.bol.kalaha.web.api.*;
import com.bol.kalaha.web.dto.CreateJoinGameDto;
import org.mapstruct.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface GameMapper {

    CreateJoinGameDto mapCreateJoinGame(CreateJoinGameRequest source);

    default GameBoard mapResult(com.bol.kalaha.domain.GameResult result) {
        var players = result.board().getPlayers().values().stream().map(
                player -> mapPlayer(player)).toList();
        var pits = houseMapToList(result.board().getHouseMap());
        var stores = mapStores(result.board().getStores());
        var turn = result.turn() == null ? null : GameBoard.TurnEnum.fromValue(result.turn().label().name());
        var gameResult = new GameBoard()
                .status(GameBoard.StatusEnum.valueOf(result.status().name()))
                .playerPits(pits)
                .players(players)
                .stores(stores)
                .turn(turn);
        return gameResult;
    }
    Player mapPlayer(com.bol.kalaha.domain.Player player);

    com.bol.kalaha.domain.Move mapMove(Move move);

    default Map<String, List<Pit>> houseMapToList(Map<PlayerNum, Map<Integer,
            com.bol.kalaha.domain.Pit>> houseMap) {
        Map<String, List<Pit>> mappedMap = new HashMap<>();
        for (Map.Entry<PlayerNum, Map<Integer, com.bol.kalaha.domain.Pit>> entry : houseMap.entrySet()) {
            PlayerNum playerNum = entry.getKey();
            Map<Integer, com.bol.kalaha.domain.Pit> innerMap = entry.getValue();

            // Transform the inner map to a list of houses
            List<Pit> houseList = innerMap
                    .entrySet().stream()
                    .map(houseEntry -> new Pit().seeds(houseEntry.getValue().count()).index(houseEntry.getKey()))
                    .toList();

            mappedMap.put(playerNum.name(), houseList);
        }
        return mappedMap;
    }

    Pit mapToApiPit(com.bol.kalaha.domain.Pit pit);

    Map<String, Pit> mapStores(Map<PlayerNum, com.bol.kalaha.domain.Pit> stores);
}
