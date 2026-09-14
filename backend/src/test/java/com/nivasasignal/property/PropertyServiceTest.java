package com.nivasasignal.property;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PropertyServiceTest {

    @Mock
    private PropertyRepository propertyRepository;

    @InjectMocks
    private PropertyService propertyService;

    private Property sampleProperty;

    @BeforeEach
    void setUp() {
        sampleProperty = Property.builder()
                .id(1L)
                .title("2BHK Luxury Apartment")
                .propertyType(PropertyType.FLAT)
                .bhk("2BHK")
                .city("Pune")
                .locality("Kothrud")
                .priceInr(8500000L)
                .areaSqft(new BigDecimal("1100.00"))
                .availabilityStatus(AvailabilityStatus.AVAILABLE)
                .truthScore(90)
                .build();
    }

    @Test
    @DisplayName("createProperty should save and return property")
    void testCreateProperty() {
        when(propertyRepository.save(any(Property.class))).thenReturn(sampleProperty);

        Property created = propertyService.createProperty(sampleProperty);

        assertThat(created).isNotNull();
        assertThat(created.getTitle()).isEqualTo("2BHK Luxury Apartment");
        verify(propertyRepository).save(sampleProperty);
    }

    @Test
    @DisplayName("getAllProperties should return all properties")
    void testGetAllProperties() {
        when(propertyRepository.findAll()).thenReturn(List.of(sampleProperty));

        List<Property> properties = propertyService.getAllProperties();

        assertThat(properties).hasSize(1);
        assertThat(properties.get(0).getCity()).isEqualTo("Pune");
        verify(propertyRepository).findAll();
    }

    @Test
    @DisplayName("getPropertyById should return property when found")
    void testGetPropertyById_Found() {
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(sampleProperty));

        Property found = propertyService.getPropertyById(1L);

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(1L);
        verify(propertyRepository).findById(1L);
    }

    @Test
    @DisplayName("getPropertyById should throw EntityNotFoundException when not found")
    void testGetPropertyById_NotFound() {
        when(propertyRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> propertyService.getPropertyById(999L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Property not found with id: 999");

        verify(propertyRepository).findById(999L);
    }

    @Test
    @DisplayName("getPropertiesByCity should return matching properties")
    void testGetPropertiesByCity() {
        when(propertyRepository.findByCityIgnoreCase("pune")).thenReturn(List.of(sampleProperty));

        List<Property> properties = propertyService.getPropertiesByCity("pune");

        assertThat(properties).hasSize(1);
        verify(propertyRepository).findByCityIgnoreCase("pune");
    }

    @Test
    @DisplayName("getPropertiesByType should return matching properties")
    void testGetPropertiesByType() {
        when(propertyRepository.findByPropertyType(PropertyType.FLAT)).thenReturn(List.of(sampleProperty));

        List<Property> properties = propertyService.getPropertiesByType(PropertyType.FLAT);

        assertThat(properties).hasSize(1);
        verify(propertyRepository).findByPropertyType(PropertyType.FLAT);
    }

    @Test
    @DisplayName("getPropertiesByStatus should return matching properties")
    void testGetPropertiesByStatus() {
        when(propertyRepository.findByAvailabilityStatus(AvailabilityStatus.AVAILABLE)).thenReturn(List.of(sampleProperty));

        List<Property> properties = propertyService.getPropertiesByStatus(AvailabilityStatus.AVAILABLE);

        assertThat(properties).hasSize(1);
        verify(propertyRepository).findByAvailabilityStatus(AvailabilityStatus.AVAILABLE);
    }
}
