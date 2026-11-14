package com.nivasasignal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "source_listings")
@Getter
@Setter
@NoArgsConstructor
public class SourceListing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "property_id")
    private Long propertyId;

    @Column(name = "source_name")
    private String sourceName;

    @Column(name = "external_listing_id")
    private String externalListingId;

    @Column(name = "source_url")
    private String sourceUrl;

    @Column(name = "source_property_type")
    private String sourcePropertyType;

    @Column(name = "source_bhk")
    private String sourceBhk;

    @Column(name = "source_area_sqft")
    private BigDecimal sourceAreaSqft;

    @Column(name = "source_price_inr")
    private Long sourcePriceInr;

    @Column(name = "source_status")
    private String sourceStatus;

    @Column(name = "last_seen_at")
    private LocalDateTime lastSeenAt;
}
