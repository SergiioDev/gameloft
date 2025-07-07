package io.test.gameloft.models.entity;

import io.test.gameloft.models.entity.junction_entities.CampaignItemEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "campaigns")
public class CampaignEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    public String game;
    public String name;
    public Double priority;

    public int minLevel;
    public int maxLevel;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(columnDefinition = "text[]")
    public Set<String> countries;

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL)
    public Set<CampaignItemEntity> campaignItems;

    public Instant startDate;
    public Instant endDate;
    public Instant lastUpdated;

    public boolean enabled;
}
