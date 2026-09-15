package com.nivasasignal.property.dto;

import com.nivasasignal.property.AvailabilityStatus;
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
}
