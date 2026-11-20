package com.nivasasignal.service;

import com.nivasasignal.entity.SourceListing;
import com.nivasasignal.repository.PropertyRepository;
import com.nivasasignal.repository.SourceListingRepository;
import com.nivasasignal.source.connector.ExternalListing;
import com.nivasasignal.source.connector.SourceConnector;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SourceImportService {

    private final PropertyRepository propertyRepository;
    private final SourceListingRepository sourceListingRepository;
    private final List<SourceConnector> sourceConnectors;

    public int importListings(Long propertyId, String city) {

        boolean propertyExists = propertyRepository.existsById(propertyId);

        if (!propertyExists) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Property not found"
            );
        }

        int importedCount = 0;

        for (SourceConnector connector : sourceConnectors) {

            List<ExternalListing> listings =
                    connector.fetchListings(city);

            for (ExternalListing externalListing : listings) {

                SourceListing sourceListing =
                        sourceListingRepository
                                .findBySourceNameAndExternalListingId(
                                        connector.sourceName(),
                                        externalListing.externalListingId()
                                )
                                .orElse(new SourceListing());

                sourceListing.setPropertyId(propertyId);
                sourceListing.setSourceName(connector.sourceName());
                sourceListing.setExternalListingId(
                        externalListing.externalListingId()
                );
                sourceListing.setSourceUrl(
                        externalListing.sourceUrl()
                );
                sourceListing.setSourcePropertyType(
                        externalListing.propertyType()
                );
                sourceListing.setSourceBhk(
                        externalListing.bhk()
                );
                sourceListing.setSourceAreaSqft(
                        externalListing.areaSqft()
                );
                sourceListing.setSourcePriceInr(
                        externalListing.priceInr()
                );
                sourceListing.setSourceStatus(
                        externalListing.status()
                );
                sourceListing.setLastSeenAt(
                        externalListing.lastSeenAt()
                );

                sourceListingRepository.save(sourceListing);

                importedCount = importedCount + 1;
            }
        }

        return importedCount;
    }
}
