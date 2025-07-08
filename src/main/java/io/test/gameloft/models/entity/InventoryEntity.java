package io.test.gameloft.models.entity;

import io.test.gameloft.models.entity.junction_entities.InventoryItemEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inventories")
public class InventoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @OneToOne
    public PlayerEntity player;

    public int cash;

    public int coins;

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL)
    public Set<InventoryItemEntity> inventoryItems;
}
