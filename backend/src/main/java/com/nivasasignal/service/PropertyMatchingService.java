package com.nivasasignal.service;

import com.nivasasignal.entity.Property;
import com.nivasasignal.enums.AvailabilityStatus;
import com.nivasasignal.enums.PropertyType;
import com.nivasasignal.repository.PropertyRepository;
import com.nivasasignal.source.connector.ExternalListing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
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

        Optional<Property> exactMatch =
                propertyRepository
                        .findFirstByTitleIgnoreCaseAndCityIgnoreCaseAndLocalityIgnoreCaseAndPropertyTypeAndBhk(
                                externalListing.title(),
                                externalListing.city(),
                                externalListing.locality(),
                                propertyType,
                                externalListing.bhk()
                        );

        if (exactMatch.isPresent()) {
            return exactMatch.get();
        }

        List<Property> candidates =
                propertyRepository
                        .findByCityIgnoreCaseAndLocalityIgnoreCaseAndPropertyTypeAndBhk(
                                externalListing.city(),
                                externalListing.locality(),
                                propertyType,
                                externalListing.bhk()
                        );

        Property bestMatch = null;
        int highestScore = 0;

        for (Property candidate : candidates) {

            int score = calculateMatchScore(
                    candidate,
                    externalListing
            );

            if (score > highestScore) {
                highestScore = score;
                bestMatch = candidate;
            }
        }

        if (bestMatch != null && highestScore >= 60) {
            return bestMatch;
        }

        return createProperty(externalListing, propertyType);
    }

    private int calculateMatchScore(
            Property property,
            ExternalListing externalListing
    ) {
        int score = 0;

        String propertyTitle = normalize(property.getTitle());
        String sourceTitle = normalize(externalListing.title());

        if (propertyTitle.equals(sourceTitle)) {
            score = score + 50;

        } else if (propertyTitle.contains(sourceTitle)
                || sourceTitle.contains(propertyTitle)) {

            score = score + 30;
        }

        if (isAreaSimilar(
                property.getAreaSqft(),
                externalListing.areaSqft()
        )) {
            score = score + 30;
        }

        if (isPriceSimilar(
                property.getPriceInr(),
                externalListing.priceInr()
        )) {
            score = score + 20;
        }

        return score;
    }

    private boolean isAreaSimilar(
            BigDecimal propertyArea,
            BigDecimal sourceArea
    ) {
        if (propertyArea == null || sourceArea == null) {
            return false;
        }

        BigDecimal difference =
                propertyArea.subtract(sourceArea).abs();

        return difference.compareTo(
                BigDecimal.valueOf(20)
        ) <= 0;
    }

    private boolean isPriceSimilar(
            Long propertyPrice,
            Long sourcePrice
    ) {
        if (propertyPrice == null
                || sourcePrice == null
                || propertyPrice == 0) {

            return false;
        }

        double priceDifference = Math.abs(
                propertyPrice - sourcePrice
        );

        double percentage =
                (priceDifference * 100.0) / propertyPrice;

        return percentage <= 10;
    }

    private String normalize(String value) {

        if (value == null) {
            return "";
        }

        return value
                .toLowerCase()
                .replaceAll("[^a-z0-9]", "");
    }

    private Property createProperty(
            ExternalListing externalListing,
            PropertyType propertyType
    ) {
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
