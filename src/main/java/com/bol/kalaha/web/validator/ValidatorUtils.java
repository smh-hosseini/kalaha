package com.bol.kalaha.web.validator;

import com.bol.kalaha.domain.Move;
import com.bol.kalaha.domain.PlayerNum;
import com.bol.kalaha.exception.WrongMoveException;
import com.bol.kalaha.web.dto.CreateJoinGameDto;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;

public class ValidatorUtils {

    private static final List<String> PLAYERS_NUM= Arrays.stream(PlayerNum.values()).map(PlayerNum::name).toList();

    public static void validateMove(Move move) {
        if (move == null) {
            throw new WrongMoveException("Move cannot be null");
        }

        if (ObjectUtils.isEmpty(move.player()) || !PLAYERS_NUM.contains(move.player().name())) {
            throw new WrongMoveException("Player must be between ONE and TWO");
        }

        if (move.pitNum() < 1  || move.pitNum() > 6) {
            throw new WrongMoveException("Pit number must be between 1 and 6");
        }
    }

    public static void validateCreateJoinGameDto(CreateJoinGameDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("CreateJoinGameDto cannot be null");
        }
        if (dto.getPlayerName() == null || dto.getPlayerName().isBlank()) {
            throw new IllegalArgumentException("Player name cannot be null or blank");
        }
    }
}
