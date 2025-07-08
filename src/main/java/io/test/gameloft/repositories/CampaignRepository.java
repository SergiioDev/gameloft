package io.test.gameloft.repositories;

import io.test.gameloft.models.entity.CampaignEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface CampaignRepository extends JpaRepository<CampaignEntity, UUID> {
    @Query(value = """
        SELECT DISTINCT c.* 
        FROM campaigns c
        INNER JOIN campaign_items ci ON ci.campaign_id = c.id
        INNER JOIN inventory_items it ON it.item_id = ci.item_id
        INNER JOIN inventories i ON i.id = it.inventory_id
        WHERE c.enabled = true
        AND :country = ANY(c.countries)
        AND :playerLevel >= c.min_level AND :playerLevel <= c.max_level
        AND ci.is_available = true
        AND i.player_id = :playerId
        AND it.quantity > 0
        AND c.end_date >= :playerCreatedDate
    """, nativeQuery = true)
    List<CampaignEntity> matchCampaignWithPlayer(
            @Param("playerId") UUID playerId,
            @Param("country") String country,
            @Param("playerLevel") int playerLevel,
            @Param("playerCreatedDate")Instant playerCreatedDate
    );
}
