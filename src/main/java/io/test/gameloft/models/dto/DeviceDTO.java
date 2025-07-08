package io.test.gameloft.models.dto;

import io.test.gameloft.models.entity.DeviceEntity;

public record DeviceDTO(
    Long id,
    String model,
    String carrier,
    String firmware
) {
    public static DeviceDTO fromEntity(DeviceEntity deviceEntity) {
        return new DeviceDTO(
                deviceEntity.id,
                deviceEntity.model,
                deviceEntity.carrier,
                deviceEntity.firmware
        );
    }
}
