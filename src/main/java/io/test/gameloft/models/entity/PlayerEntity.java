package io.test.gameloft.models.entity;

import io.test.gameloft.models.entity.junction_entities.CampaignPlayerEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "players")
public class PlayerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    public Instant created;
    public Instant modified;
    public Instant lastSession;

    public int totalSpent;
    public int totalRefund;
    public int totalTransactions;

    public Instant lastPurchase;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    public Set<DeviceEntity> devices = Collections.emptySet();

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    public Set<CampaignPlayerEntity> campaigns = Collections.emptySet();

    public int level;
    public int xp;
    public int totalPlayTime;

    public String country;
    public String language;
    public Instant birthDate;
    public String gender;

    @OneToOne(mappedBy = "player", cascade = CascadeType.ALL)
    public InventoryEntity inventory;

    @ManyToOne
    @JoinColumn(name = "clan_id")
    public ClanEntity clan;
}
