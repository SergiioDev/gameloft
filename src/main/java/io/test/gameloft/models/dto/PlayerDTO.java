package io.test.gameloft.models.dto;

import io.test.gameloft.models.entity.DeviceEntity;
import io.test.gameloft.models.entity.PlayerEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public class PlayerDTO {
    public UUID id;

    public int totalSpent;
    public int totalRefund;
    public int totalTransactions;

    public Set<String> campaigns;
    public Set<DeviceDTO> devices;

    public int level;
    public int xp;
    public int totalPlayTime;

    public String country;
    public String language;
    public String gender;

    public HashMap<String, String> inventory;

    public Instant created;
    public Instant modified;
    public Instant lastSession;
    public Instant lastPurchase;
    public Instant birthDate;

    public static PlayerDTO fromEntity(PlayerEntity player) {
        Set<DeviceDTO> devices = Optional.ofNullable(player.getDevices())
                .orElse(Collections.emptySet())
                .stream()
                .map(DeviceDTO::fromEntity)
                .collect(Collectors.toSet());

        return new PlayerDTO(
                player.id,
                player.totalSpent,
                player.totalRefund,
                player.totalTransactions,
                player.campaigns,
                devices,
                player.level,
                player.xp,
                player.totalPlayTime,
                player.country,
                player.language,
                player.gender,
                new HashMap<>(),
                player.created,
                player.modified,
                player.lastSession,
                player.lastPurchase,
                player.birthDate
        );
    }

}