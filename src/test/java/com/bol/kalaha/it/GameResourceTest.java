package com.bol.kalaha.it;


import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bol.kalaha.web.api.CreateJoinGameRequest;
import com.bol.kalaha.web.api.GameBoard;
import com.bol.kalaha.web.api.Move;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

public class GameResourceTest extends BaseApiTest {

    private ObjectMapper mapper =  JsonMapper.builder()
            .findAndAddModules()
            .build();

    @Test
    void testCreateBoardAndGetPlay(){
        var id = createKalahaBoardAndGetId();
        var result = play(id, "ONE", 6);
        assertEquals("TWO", result.getTurn().name());
        assertEquals(0, result.getPlayerPits().get("ONE").get(5).getSeeds());
        assertEquals(1, result.getStores().get("ONE").getSeeds());
        assertEquals(7, result.getPlayerPits().get("TWO").get(0).getSeeds());

    }

    @SneakyThrows
    @Test
    void testCreateBoardAndGetPlayWrongPitOrWrongPlayer_ThrowsException(){
        var id = createKalahaBoardAndGetId();
        var move = new Move().player(Move.PlayerEnum.fromValue("ONE")).pitNum(7);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.put(
                                        "/api/v1/game/{id}/play", id)
                                .contentType(APPLICATION_JSON)
                                .content(mapper.writeValueAsString(move)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail", is("Pit number must be between 1 and 6")));

        move = new Move().player(Move.PlayerEnum.fromValue("TWO")).pitNum(6);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.put(
                                        "/api/v1/game/{id}/play", id)
                                .contentType(APPLICATION_JSON)
                                .content(mapper.writeValueAsString(move)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail", is("It is not Player TWO turn.")));

    }


    static String getId(final String location) {
        return location.substring(location.lastIndexOf("/") + 1, location.length());
    }

    @SneakyThrows
    GameBoard play(UUID id, String playerNum, int pitNum) {
        var move = new Move().player(Move.PlayerEnum.fromValue(playerNum)).pitNum(pitNum);
        String result = this.mockMvc.perform(
                        MockMvcRequestBuilders.put(
                                        "/api/v1/game/{id}/play", id)
                                .contentType(APPLICATION_JSON)
                                .content(mapper.writeValueAsString(move)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.players").exists())
                .andExpect(jsonPath("$.status", is("IN_PROGRESS")))
                .andReturn().getResponse().getContentAsString();
        return mapper.readValue(result, GameBoard.class);
    }

    @SneakyThrows
    void join(UUID id, String name) {
        var join = new CreateJoinGameRequest().playerName(name);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.put(
                                        "/api/v1/game/{id}/join", id)
                                .contentType(APPLICATION_JSON)
                                .content(mapper.writeValueAsString(join)))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    UUID createKalahaBoardAndGetId() {
        var location = this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/game")
                                .content("{\"playerName\":\"player1\"}")
                                .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(header().string(LOCATION, containsString("/game")))
                .andReturn().getResponse().getHeader(LOCATION);

        var id = getId(location);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get(location)
                                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.players").exists())
                .andExpect(jsonPath("$.status", is("WAITING_FOR_PLAYER_TWO")));

        join(UUID.fromString(id), "player2");

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get(location)
                                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.players").exists())
                .andExpect(jsonPath("$.turn", is("ONE")))
                .andExpect(jsonPath("$.playerPits.ONE[0].index", is(1)))
                .andExpect(jsonPath("$.playerPits.ONE[0].seeds", is(6)))
                .andExpect(jsonPath("$.status", is("IN_PROGRESS")));
        return UUID.fromString(id);
    }
}
