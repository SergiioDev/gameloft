package io.test.gameloft.services;

import io.test.gameloft.exceptions.PlayerNotFoundException;
import io.test.gameloft.models.dto.ClanDTO;
import io.test.gameloft.models.dto.DeviceDTO;
import io.test.gameloft.models.dto.PlayerDTO;
import io.test.gameloft.models.entity.*;
import io.test.gameloft.models.entity.junction_entities.CampaignPlayerEntity;
import io.test.gameloft.repositories.CampaignPlayerRepository;
import io.test.gameloft.repositories.CampaignRepository;
import io.test.gameloft.repositories.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileMatcherServiceTest {
    @Mock
    private CampaignPlayerRepository campaignPlayerRepository;

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private ProfileMatcherService profileMatcherService;

    @Test
    void givenPlayerId_whenMatchPlayerWithCampaign_thenReturnPlayerDTO() {
        // Given
        UUID playerId = UUID.randomUUID();
        PlayerEntity playerEntity = new PlayerEntity();
        ClanEntity clanEntity = new ClanEntity(UUID.randomUUID(), "Hello world clan");

        DeviceEntity deviceEntity = new DeviceEntity();
        deviceEntity.player = playerEntity;

        playerEntity.id = playerId;
        playerEntity.totalSpent = 100;
        playerEntity.level = 5;
        playerEntity.country = "US";
        playerEntity.created = Instant.parse("2022-05-01T00:00:00.00Z");
        playerEntity.devices = Set.of(deviceEntity);
        playerEntity.inventory = new InventoryEntity(UUID.randomUUID(),
                playerEntity,
                20,
                20,
                Collections.emptySet()
        );
        playerEntity.clan = clanEntity;

        CampaignEntity campaignEntity = new CampaignEntity();
        campaignEntity.id = UUID.randomUUID();
        campaignEntity.name = "Summer Campaign";
        campaignEntity.enabled = true;
        campaignEntity.countries = Set.of(playerEntity.country);
        campaignEntity.minLevel = 5;
        campaignEntity.maxLevel = 10;
        campaignEntity.startDate = Instant.parse("2022-06-01T00:00:00.00Z");
        campaignEntity.endDate = Instant.parse("2022-07-01T00:00:00.00Z");

        CampaignPlayerEntity campaignPlayerEntity = new CampaignPlayerEntity(UUID.randomUUID(), campaignEntity, playerEntity);

        List<CampaignEntity> campaignEntities = List.of(campaignEntity);

        playerEntity.campaigns = Set.of(campaignPlayerEntity);

        // When
        when(playerRepository.findById(playerId)).thenReturn(Optional.of(playerEntity));
        when(campaignRepository.getCampaignsForPlayer(
                playerId,
                playerEntity.country,
                playerEntity.level,
                playerEntity.created
            )
        ).thenReturn(campaignEntities);
        doNothing().when(campaignPlayerRepository).deleteByUserId(playerId);
        when(playerRepository.save(playerEntity)).thenReturn(playerEntity);

        PlayerDTO result = profileMatcherService.matchPlayerWithCampaign(playerId);

        // Then
        PlayerDTO expectedResult = new PlayerDTO(
                playerEntity.id,
                playerEntity.totalSpent,
                playerEntity.totalRefund,
                playerEntity.totalTransactions,
                Set.of("Summer Campaign"),
                Set.of( new DeviceDTO(deviceEntity.id, deviceEntity.model, deviceEntity.carrier ,deviceEntity.firmware)),
                playerEntity.level,
                playerEntity.xp,
                playerEntity.totalPlayTime,
                playerEntity.country,
                playerEntity.language,
                playerEntity.gender,
                Map.of(
                        "cash", 20,
                        "coins", 20
                ),
                playerEntity.created,
                playerEntity.modified,
                playerEntity.lastSession,
                playerEntity.lastPurchase,
                playerEntity.birthDate,
                new ClanDTO(clanEntity.id, clanEntity.name)

        );
        assertNotNull(result);
        assertEquals(expectedResult, result);

        verify(playerRepository).findById(playerId);
        verify(campaignRepository).getCampaignsForPlayer(
                playerId,
                playerEntity.country,
                playerEntity.level,
                playerEntity.created
        );
        verify(campaignPlayerRepository).deleteByUserId(playerId);
        verify(playerRepository).save(playerEntity);
    }

    @Test
    void givenPlayerId_whenNoPlayerIsFound_thenThrowPlayerNotFoundException() {
        UUID playerId = UUID.randomUUID();
        when(playerRepository.findById(playerId)).thenReturn(Optional.empty());


        assertThrows(PlayerNotFoundException.class, () -> profileMatcherService.matchPlayerWithCampaign(playerId));

        verify(playerRepository).findById(playerId);
    }
}