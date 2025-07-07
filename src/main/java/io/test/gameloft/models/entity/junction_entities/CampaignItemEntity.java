package io.test.gameloft.models.entity.junction_entities;

import io.test.gameloft.models.entity.CampaignEntity;
import io.test.gameloft.models.entity.ItemEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "campaign_items")
public class CampaignItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    public CampaignEntity campaign;

    @ManyToOne
    @JoinColumn(name = "item_id")
    public ItemEntity item;

    @Column(name = "is_available")
    public boolean isAvailable;
}
