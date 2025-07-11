package io.test.gameloft.models.entity;

import io.test.gameloft.models.entity.junction_entities.CampaignItemEntity;
import io.test.gameloft.models.entity.junction_entities.InventoryItemEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "items")
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    public String name;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    public Set<InventoryItemEntity> inventoryItems = Collections.emptySet();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    public Set<CampaignItemEntity> campaignItems = Collections.emptySet();
}
