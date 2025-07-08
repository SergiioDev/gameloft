package io.test.gameloft.controllers;

import io.test.gameloft.models.dto.PlayerDTO;
import io.test.gameloft.services.ProfileMatcherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ProfileMatcherController {
    private final ProfileMatcherService profileMatcherService;

    public ProfileMatcherController(ProfileMatcherService profileMatcherService) {
        this.profileMatcherService = profileMatcherService;
    }
    @GetMapping("/get_client_config/{player_id}")
    public PlayerDTO getPlayer(@PathVariable("player_id") UUID playerId) {
        return profileMatcherService.matchPlayerWithCampaign(playerId);
    }
}

