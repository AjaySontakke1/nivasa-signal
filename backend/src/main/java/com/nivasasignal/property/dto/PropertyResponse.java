package com.nivasasignal.property.dto;

import com.nivasasignal.property.AvailabilityStatus;
import com.nivasasignal.property.Property;
import com.nivasasignal.property.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyResponse {

    private Long id;
    private String title;
    private PropertyType propertyType;
    private String bhk;
    private String city;
    private String locality;
    private String address;
    private Long priceInr;
    private BigDecimal areaSqft;
    private AvailabilityStatus availabilityStatus;
    private Integer truthScore;
    private LocalDateTime lastCheckedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PropertyResponse fromEntity(Property property) {
        if (property == null) {
            return null;
        }
        return PropertyResponse.builder()
                .id(property.getId())
                .title(property.getTitle())
                .propertyType(property.getPropertyType())
                .bhk(property.getBhk())
                .city(property.getCity())
                .locality(property.getLocality())
                .address(property.getAddress())
                .priceInr(property.getPriceInr())
                .areaSqft(property.getAreaSqft())
                .availabilityStatus(property.getAvailabilityStatus())
                .truthScore(property.getTruthScore())
                .lastCheckedAt(property.getLastCheckedAt())
                .createdAt(property.getCreatedAt())
                .updatedAt(property.getUpdatedAt())
                .build();
    }
}
