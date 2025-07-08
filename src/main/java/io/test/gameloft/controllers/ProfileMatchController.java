package io.test.gameloft.controllers;

import io.test.gameloft.models.dto.PlayerDTO;
import io.test.gameloft.services.MatcherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ProfileMatchController {
    private final MatcherService matcherService;

    public ProfileMatchController(MatcherService matcherService) {
        this.matcherService = matcherService;
    }
    @GetMapping("/get_client_config/{player_id}")
    public PlayerDTO getPlayer(@PathVariable("player_id") UUID playerId) {
        return matcherService.matchPlayerWithCampaign(playerId);
    }
}

