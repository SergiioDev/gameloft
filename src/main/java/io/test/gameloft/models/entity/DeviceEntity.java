package io.test.gameloft.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "devices")
public class DeviceEntity {
    @Id
    public Long id;

    public String model;
    public String carrier;
    public String firmware;

    @ManyToOne
    @JoinColumn(name = "player_id")
    public PlayerEntity player;
}
