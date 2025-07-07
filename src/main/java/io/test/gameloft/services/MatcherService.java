package io.test.gameloft.services;

import io.test.gameloft.exceptions.PlayerNotFoundException;
import io.test.gameloft.models.dto.PlayerDTO;
import io.test.gameloft.models.entity.PlayerEntity;
import io.test.gameloft.repositories.PlayerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class MatcherService {
    final PlayerRepository playerRepository;

    public MatcherService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Transactional
    public PlayerDTO matchPlayerWithCampaign(UUID playerId) {
        PlayerEntity player = playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException("Player with id " + playerId + " not found"));

        return PlayerDTO.fromEntity(player);
    }

}
