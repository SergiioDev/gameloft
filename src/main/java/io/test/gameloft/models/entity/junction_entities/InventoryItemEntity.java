package io.test.gameloft.models.entity.junction_entities;

import io.test.gameloft.models.entity.InventoryEntity;
import io.test.gameloft.models.entity.ItemEntity;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "inventory_items")
public class InventoryItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    public InventoryEntity inventory;

    @ManyToOne
    @JoinColumn(name = "item_id")
    public ItemEntity item;

    public int quantity;
}
