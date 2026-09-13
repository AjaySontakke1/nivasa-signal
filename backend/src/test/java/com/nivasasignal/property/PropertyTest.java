package com.nivasasignal.property;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PropertyTest {

    @Test
    @DisplayName("Should create property using builder and verify all fields")
    void testPropertyBuilderAndGetters() {
        LocalDateTime now = LocalDateTime.now();

        Property property = Property.builder()
                .id(1L)
                .title("Luxury 3BHK Apartment")
                .propertyType(PropertyType.FLAT)
                .bhk("3BHK")
                .city("Bengaluru")
                .locality("Whitefield")
                .address("Prestige Ozone, Whitefield Main Rd")
                .priceInr(15000000L)
                .areaSqft(new BigDecimal("1850.50"))
                .availabilityStatus(AvailabilityStatus.AVAILABLE)
                .truthScore(95)
                .lastCheckedAt(now)
                .createdAt(now)
                .updatedAt(now)
                .build();

        assertThat(property.getId()).isEqualTo(1L);
        assertThat(property.getTitle()).isEqualTo("Luxury 3BHK Apartment");
        assertThat(property.getPropertyType()).isEqualTo(PropertyType.FLAT);
        assertThat(property.getBhk()).isEqualTo("3BHK");
        assertThat(property.getCity()).isEqualTo("Bengaluru");
        assertThat(property.getLocality()).isEqualTo("Whitefield");
        assertThat(property.getAddress()).isEqualTo("Prestige Ozone, Whitefield Main Rd");
        assertThat(property.getPriceInr()).isEqualTo(15000000L);
        assertThat(property.getAreaSqft()).isEqualByComparingTo(new BigDecimal("1850.50"));
        assertThat(property.getAvailabilityStatus()).isEqualTo(AvailabilityStatus.AVAILABLE);
        assertThat(property.getTruthScore()).isEqualTo(95);
        assertThat(property.getLastCheckedAt()).isEqualTo(now);
        assertThat(property.getCreatedAt()).isEqualTo(now);
        assertThat(property.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    @DisplayName("Should verify all PropertyType enum values")
    void testPropertyTypeEnumValues() {
        assertThat(PropertyType.values()).containsExactly(
                PropertyType.FLAT,
                PropertyType.LAND,
                PropertyType.SHOP,
                PropertyType.OFFICE,
                PropertyType.VILLA
        );
    }

    @Test
    @DisplayName("Should verify all AvailabilityStatus enum values")
    void testAvailabilityStatusEnumValues() {
        assertThat(AvailabilityStatus.values()).containsExactly(
                AvailabilityStatus.AVAILABLE,
                AvailabilityStatus.RESERVED,
                AvailabilityStatus.SOLD,
                AvailabilityStatus.INACTIVE,
                AvailabilityStatus.UNVERIFIED
        );
    }

    @Test
    @DisplayName("PrePersist should initialize timestamps and default availabilityStatus when null")
    void testPrePersist() {
        Property property = new Property();
        property.setTitle("Green Valley Plot");
        property.setPropertyType(PropertyType.LAND);
        property.setCity("Pune");

        property.onCreate();

        assertThat(property.getCreatedAt()).isNotNull();
        assertThat(property.getUpdatedAt()).isNotNull();
        assertThat(property.getAvailabilityStatus()).isEqualTo(AvailabilityStatus.UNVERIFIED);
    }

    @Test
    @DisplayName("PreUpdate should refresh updatedAt timestamp")
    void testPreUpdate() {
        LocalDateTime past = LocalDateTime.now().minusDays(1);
        Property property = Property.builder()
                .title("Cozy Villa")
                .propertyType(PropertyType.VILLA)
                .city("Goa")
                .createdAt(past)
                .updatedAt(past)
                .build();

        property.onUpdate();

        assertThat(property.getUpdatedAt()).isAfter(past);
    }
}
