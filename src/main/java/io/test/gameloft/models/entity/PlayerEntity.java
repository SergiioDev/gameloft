package io.test.gameloft.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
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

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(columnDefinition = "text[]")
    public Set<String> campaigns = new HashSet<>();

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<DeviceEntity> devices = new HashSet<>();

    public int level;
    public int xp;
    public int totalPlayTime;

    public String country;
    public String language;
    public Instant birthDate;
    public String gender;

    @OneToOne(mappedBy = "player", cascade = CascadeType.ALL)
    public InventoryEntity inventory;
}
