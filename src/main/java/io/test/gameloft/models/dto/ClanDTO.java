package io.test.gameloft.models.dto;

import java.util.UUID;
import io.test.gameloft.models.entity.ClanEntity;

public record ClanDTO(
        UUID id,
        String name
) {
    public static ClanDTO fromEntity(ClanEntity clanEntity) {
        return new ClanDTO(
                clanEntity.id,
                clanEntity.name
        );
    }
}
