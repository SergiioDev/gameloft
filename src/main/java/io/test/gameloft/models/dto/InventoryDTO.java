package io.test.gameloft.models.dto;

import io.test.gameloft.models.entity.InventoryEntity;

import java.util.HashMap;

public record InventoryDTO(
        HashMap<String, Integer> inventory
) {
    public static InventoryDTO fromEntity(InventoryEntity inventoryEntity) {
        HashMap<String, Integer> inventory = new HashMap<>();

        inventory.put("cash", inventoryEntity.cash);
        inventory.put("coins", inventoryEntity.coins);

        inventoryEntity.inventoryItems.forEach(item -> inventory.put(item.item.name, item.quantity));

        return new InventoryDTO(inventory);
    }
}
