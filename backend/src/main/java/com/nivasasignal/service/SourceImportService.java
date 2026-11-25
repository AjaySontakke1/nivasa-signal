package com.nivasasignal.service;

import com.nivasasignal.entity.Property;
import com.nivasasignal.entity.SourceListing;
import com.nivasasignal.repository.SourceListingRepository;
import com.nivasasignal.source.connector.ExternalListing;
import com.nivasasignal.source.connector.SourceConnector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SourceImportService {

    private final PropertyMatchingService propertyMatchingService;
    private final SourceListingRepository sourceListingRepository;
    private final List<SourceConnector> sourceConnectors;

    public int importListings(String city) {

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
                                .orElse(null);

                if (sourceListing == null) {

                    Property property = propertyMatchingService
                            .findOrCreate(externalListing);

                    sourceListing = new SourceListing();

                    sourceListing.setPropertyId(property.getId());
                }

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
