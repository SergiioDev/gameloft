package io.test.gameloft.services;

import io.test.gameloft.exceptions.PlayerNotFoundException;
import io.test.gameloft.models.dto.PlayerDTO;
import io.test.gameloft.models.entity.CampaignEntity;
import io.test.gameloft.models.entity.PlayerEntity;
import io.test.gameloft.models.entity.junction_entities.CampaignPlayerEntity;
import io.test.gameloft.repositories.CampaignPlayerRepository;
import io.test.gameloft.repositories.CampaignRepository;
import io.test.gameloft.repositories.PlayerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MatcherService {
    final PlayerRepository playerRepository;
    final CampaignRepository campaignRepository;
    final CampaignPlayerRepository campaignPlayerRepository;

    public MatcherService(PlayerRepository playerRepository, CampaignRepository campaignRepository, CampaignPlayerRepository campaignPlayerRepository) {
        this.playerRepository = playerRepository;
        this.campaignRepository = campaignRepository;
        this.campaignPlayerRepository = campaignPlayerRepository;
    }

    @Transactional
    public PlayerDTO matchPlayerWithCampaign(UUID playerId) {
        PlayerEntity player = playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException("Player with id " + playerId + " not found"));

        List<CampaignEntity> playerCampaigns = campaignRepository.matchCampaignWithPlayer(
                player.id,
                player.country,
                player.level,
                player.created
        );


        campaignPlayerRepository.deleteByUserId(playerId);

        player.campaigns = playerCampaigns.stream().map(campaignEntity ->
                new CampaignPlayerEntity(
                    null,
                    campaignEntity,
                    player
                )
        ).collect(Collectors.toSet());


        PlayerEntity updatedPlayer = playerRepository.save(player);

        return PlayerDTO.fromEntity(updatedPlayer, playerCampaigns);
    }

}
