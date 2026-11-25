package com.nivasasignal.service;

import com.nivasasignal.entity.Property;
import com.nivasasignal.enums.AvailabilityStatus;
import com.nivasasignal.enums.PropertyType;
import com.nivasasignal.repository.PropertyRepository;
import com.nivasasignal.source.connector.ExternalListing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PropertyMatchingService {

    private final PropertyRepository propertyRepository;

    public Property findOrCreate(
            ExternalListing externalListing
    ) {
        PropertyType propertyType = PropertyType.valueOf(
                externalListing.propertyType()
                        .trim()
                        .toUpperCase()
        );

        Optional<Property> existingProperty =
                propertyRepository
                        .findFirstByTitleIgnoreCaseAndCityIgnoreCaseAndLocalityIgnoreCaseAndPropertyTypeAndBhk(
                                externalListing.title(),
                                externalListing.city(),
                                externalListing.locality(),
                                propertyType,
                                externalListing.bhk()
                        );

        if (existingProperty.isPresent()) {
            return existingProperty.get();
        }

        Property property = new Property();

        property.setTitle(externalListing.title());
        property.setCity(externalListing.city());
        property.setLocality(externalListing.locality());
        property.setPropertyType(propertyType);
        property.setBhk(externalListing.bhk());
        property.setAreaSqft(externalListing.areaSqft());
        property.setPriceInr(externalListing.priceInr());
        property.setTruthScore(0);
        property.setLastCheckedAt(
                externalListing.lastSeenAt()
        );

        if (externalListing.status() != null
                && externalListing.status().equalsIgnoreCase("AVAILABLE")) {

            property.setAvailabilityStatus(
                    AvailabilityStatus.AVAILABLE
            );

        } else {
            property.setAvailabilityStatus(
                    AvailabilityStatus.UNVERIFIED
            );
        }

        return propertyRepository.save(property);
    }
}
