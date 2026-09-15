package com.nivasasignal.property.dto;

import com.nivasasignal.property.AvailabilityStatus;
import com.nivasasignal.property.Property;
import com.nivasasignal.property.PropertyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePropertyRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Property type is required")
    private PropertyType propertyType;

    private String bhk;

    @NotBlank(message = "City is required")
    private String city;

    private String locality;

    private String address;

    @Positive(message = "Price must be greater than zero")
    private Long priceInr;

    @Positive(message = "Area must be greater than zero")
    private BigDecimal areaSqft;

    private AvailabilityStatus availabilityStatus;

    public Property toEntity() {
        return Property.builder()
                .title(this.title)
                .propertyType(this.propertyType)
                .bhk(this.bhk)
                .city(this.city)
                .locality(this.locality)
                .address(this.address)
                .priceInr(this.priceInr)
                .areaSqft(this.areaSqft)
                .availabilityStatus(this.availabilityStatus != null ? this.availabilityStatus : AvailabilityStatus.UNVERIFIED)
                .build();
    }
}
