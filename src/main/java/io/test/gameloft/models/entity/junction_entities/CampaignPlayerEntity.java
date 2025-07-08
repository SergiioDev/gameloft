package io.test.gameloft.models.entity.junction_entities;

import io.test.gameloft.models.entity.CampaignEntity;
import io.test.gameloft.models.entity.PlayerEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "campaign_players")
public class CampaignPlayerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    public CampaignEntity campaign;

    @ManyToOne
    @JoinColumn(name = "player_id")
    public PlayerEntity player;
}
