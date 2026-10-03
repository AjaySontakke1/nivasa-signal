package com.nivasasignal.source;

import com.nivasasignal.entity.Property;
import com.nivasasignal.repository.PropertyRepository;
import com.nivasasignal.source.dto.CreateSourceListingRequest;
import com.nivasasignal.source.dto.SourceListingResponse;
import com.nivasasignal.source.dto.UpdateSourceListingRequest;
import com.nivasasignal.verification.TruthScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SourceListingService {

    private final SourceListingRepository sourceListingRepository;
    private final PropertyRepository propertyRepository;
    private final TruthScoreService truthScoreService;

    public SourceListingResponse createSourceListing(
            CreateSourceListingRequest request
    ) {
        Property property = propertyRepository.findById(request.propertyId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Property not found"
                ));

        SourceListing sourceListing = new SourceListing();

        sourceListing.setPropertyId(request.propertyId());
        sourceListing.setSourceName(request.sourceName());
        sourceListing.setExternalListingId(request.externalListingId());
        sourceListing.setSourceUrl(request.sourceUrl());
        sourceListing.setSourcePriceInr(request.sourcePriceInr());
        sourceListing.setSourceStatus(request.sourceStatus());
        sourceListing.setLastSeenAt(LocalDateTime.now());

        SourceListing savedListing =
                sourceListingRepository.save(sourceListing);

        int truthScore = truthScoreService.calculateScore(property);

        property.setTruthScore(truthScore);

        propertyRepository.save(property);

        return toResponse(savedListing);
    }

    public SourceListingResponse updateSourceListing(
            Long id,
            UpdateSourceListingRequest request
    ) {
        SourceListing sourceListing = sourceListingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Source listing not found"
                ));

        sourceListing.setSourceUrl(request.sourceUrl());
        sourceListing.setSourcePriceInr(request.sourcePriceInr());
        sourceListing.setSourceStatus(request.sourceStatus());
        sourceListing.setLastSeenAt(LocalDateTime.now());

        SourceListing updatedListing =
                sourceListingRepository.save(sourceListing);

        Property property = propertyRepository.findById(
                sourceListing.getPropertyId()
        ).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Property not found"
        ));

        int truthScore = truthScoreService.calculateScore(property);

        property.setTruthScore(truthScore);

        propertyRepository.save(property);

        return toResponse(updatedListing);
    }

    public List<SourceListingResponse> getListingsByPropertyId(
            Long propertyId
    ) {
        return sourceListingRepository.findByPropertyId(propertyId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private SourceListingResponse toResponse(
            SourceListing sourceListing
    ) {
        return new SourceListingResponse(
                sourceListing.getId(),
                sourceListing.getPropertyId(),
                sourceListing.getSourceName(),
                sourceListing.getExternalListingId(),
                sourceListing.getSourceUrl(),
                sourceListing.getSourcePriceInr(),
                sourceListing.getSourceStatus(),
                sourceListing.getLastSeenAt()
        );
    }
}
