package io.test.gameloft.models.dto;

import io.test.gameloft.models.entity.CampaignEntity;
import io.test.gameloft.models.entity.PlayerEntity;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public record PlayerDTO(
    UUID id,
    int totalSpent,
    int totalRefund,
    int totalTransactions,
    Set<String> activeCampaigns,
    Set<DeviceDTO> devices,
    int level,
    int xp,
    int totalPlayTime,
    String country,
    String language,
    String gender,
    Map<String, Integer> inventory,
    Instant created,
    Instant modified,
    Instant lastSession,
    Instant lastPurchase,
    Instant birthDate,
    ClanDTO clan
) {
    public static PlayerDTO fromEntity(PlayerEntity player, List<CampaignEntity> campaigns) {
        Set<DeviceDTO> devices = player.devices
                .stream()
                .map(DeviceDTO::fromEntity)
                .collect(Collectors.toSet());

        Set<String> campaignsNames = campaigns
                .stream()
                .map(campaignEntity -> campaignEntity.name)
                .collect(Collectors.toSet());

        InventoryDTO inventoryDTO = InventoryDTO.fromEntity(player.inventory);

        return new PlayerDTO(
                player.id,
                player.totalSpent,
                player.totalRefund,
                player.totalTransactions,
                campaignsNames,
                devices,
                player.level,
                player.xp,
                player.totalPlayTime,
                player.country,
                player.language,
                player.gender,
                inventoryDTO.inventory(),
                player.created,
                player.modified,
                player.lastSession,
                player.lastPurchase,
                player.birthDate,
                ClanDTO.fromEntity(player.clan)
        );
    }

}