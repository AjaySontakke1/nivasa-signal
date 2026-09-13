package com.nivasasignal.property;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "properties")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "property_type", nullable = false, length = 50)
    private PropertyType propertyType;

    @Column(length = 20)
    private String bhk;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(length = 150)
    private String locality;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(name = "price_inr")
    private Long priceInr;

    @Column(name = "area_sqft", precision = 12, scale = 2)
    private BigDecimal areaSqft;

    @Enumerated(EnumType.STRING)
    @Column(name = "availability_status", nullable = false, length = 50)
    private AvailabilityStatus availabilityStatus;

    @Column(name = "truth_score")
    private Integer truthScore;

    @Column(name = "last_checked_at")
    private LocalDateTime lastCheckedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if (this.updatedAt == null) {
            this.updatedAt = LocalDateTime.now();
        }
        if (this.availabilityStatus == null) {
            this.availabilityStatus = AvailabilityStatus.UNVERIFIED;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
