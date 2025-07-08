package io.test.gameloft.repositories;

import io.test.gameloft.models.entity.CampaignEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CampaignPlayerRepository extends JpaRepository<CampaignEntity, UUID> {
    @Modifying
    @Query(value = """
        DELETE
        FROM campaign_players 
        WHERE campaign_players.player_id = :playerId
        
    """, nativeQuery = true)
    void deleteByUserId(
            @Param("playerId") UUID playerId
    );
}
