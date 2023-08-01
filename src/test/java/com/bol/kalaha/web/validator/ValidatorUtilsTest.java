package com.bol.kalaha.web.validator;

import com.bol.kalaha.domain.Move;
import com.bol.kalaha.domain.PlayerNum;
import com.bol.kalaha.exception.WrongMoveException;
import com.bol.kalaha.web.dto.CreateJoinGameDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorUtilsTest {

    @Test
    public void testValidateMove_ValidMove() {
        // Arrange
        Move move = new Move(PlayerNum.ONE, 4);

        // Act & Assert (no exception should be thrown)
        assertDoesNotThrow(() -> ValidatorUtils.validateMove(move));
    }

    @Test
    public void testValidateMove_NullMove() {
        // Act & Assert (expecting WrongMoveException with "Move cannot be null" message)
        assertThrows(WrongMoveException.class, () -> ValidatorUtils.validateMove(null));
    }


    @Test
    public void testValidateMove_InvalidPitNum() {
        // Arrange
        Move move = new Move(PlayerNum.ONE, 7);

        // Act & Assert (expecting WrongMoveException with "Pit number must be between 1 and 6" message)
        assertThrows(WrongMoveException.class, () -> ValidatorUtils.validateMove(move));
    }

    @Test
    public void testValidateCreateJoinGameDto_ValidDto() {
        // Arrange
        CreateJoinGameDto dto = new CreateJoinGameDto("Player 1");

        // Act & Assert (no exception should be thrown)
        assertDoesNotThrow(() -> ValidatorUtils.validateCreateJoinGameDto(dto));
    }

    @Test
    public void testValidateCreateJoinGameDto_NullDto() {
        // Act & Assert (expecting IllegalArgumentException with "CreateJoinGameDto cannot be null" message)
        assertThrows(IllegalArgumentException.class, () -> ValidatorUtils.validateCreateJoinGameDto(null));
    }

    @Test
    public void testValidateCreateJoinGameDto_NullOrBlankPlayerName() {
        // Arrange
        CreateJoinGameDto dto1 = new CreateJoinGameDto(null);
        CreateJoinGameDto dto2 = new CreateJoinGameDto("");

        // Act & Assert (expecting IllegalArgumentException with "Player name cannot be null or blank" message)
        assertThrows(IllegalArgumentException.class, () -> ValidatorUtils.validateCreateJoinGameDto(dto1));
        assertThrows(IllegalArgumentException.class, () -> ValidatorUtils.validateCreateJoinGameDto(dto2));
    }
}