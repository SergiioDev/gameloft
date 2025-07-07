package io.test.gameloft.models.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "devices")
public class DeviceEntity {
    @Id
    private Long id;

    public String model;
    public String carrier;
    public String firmware;

    @ManyToOne
    @JoinColumn(name = "player_id")
    public PlayerEntity player;
}
