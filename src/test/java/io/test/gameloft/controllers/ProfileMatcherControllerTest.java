package io.test.gameloft.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.test.gameloft.models.dto.ClanDTO;
import io.test.gameloft.models.dto.PlayerDTO;
import io.test.gameloft.services.ProfileMatcherService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProfileMatcherController.class)
class ProfileMatcherControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProfileMatcherService profileMatcherService;

    @SneakyThrows
    @Test
    void givenPlayerId_whenMatchPlayerWithCampaign_thenReturn_OK() {
        // given
        UUID playerId = UUID.randomUUID();
        PlayerDTO expectedResponse = new PlayerDTO(
                playerId,
                20,
                20,
                20,
                Collections.emptySet(),
                Collections.emptySet(),
                5,
                500,
                2000,
                "CA",
                "EN",
                "male",
                Collections.emptyMap(),
                Instant.now(),
                Instant.now(),
                Instant.now(),
                Instant.now(),
                Instant.now(),
                new ClanDTO(UUID.randomUUID(), "Hello world clan")
        );

        // when
        when(profileMatcherService.matchPlayerWithCampaign(playerId)).thenReturn(expectedResponse);

        // then
        mockMvc.perform(
                        get("/get_client_config/{player_id}", playerId)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));

    }
}