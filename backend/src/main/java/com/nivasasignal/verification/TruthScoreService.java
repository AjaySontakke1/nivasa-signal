package com.nivasasignal.verification;

import com.nivasasignal.entity.Property;
import com.nivasasignal.enums.AvailabilityStatus;
import com.nivasasignal.source.SourceListing;
import com.nivasasignal.source.SourceListingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TruthScoreService {

    private final SourceListingRepository sourceListingRepository;

    public int calculateScore(Property property) {

        int score = 0;

        if (property.getAvailabilityStatus() == AvailabilityStatus.AVAILABLE) {
            score = score + 30;
        }

        List<SourceListing> listings =
                sourceListingRepository.findByPropertyId(property.getId());

        int activeListingCount = 0;
        boolean hasRecentActiveListing = false;

        for (SourceListing listing : listings) {

            if (listing.getSourceStatus() != null
                    && listing.getSourceStatus().equalsIgnoreCase("AVAILABLE")) {

                activeListingCount = activeListingCount + 1;

                if (listing.getLastSeenAt() != null) {
                    boolean checkedInLast24Hours =
                            listing.getLastSeenAt()
                                    .isAfter(LocalDateTime.now().minusDays(1));

                    if (checkedInLast24Hours) {
                        hasRecentActiveListing = true;
                    }
                }
            }
        }

        if (activeListingCount >= 1) {
            score = score + 30;
        }

        if (activeListingCount >= 2) {
            score = score + 20;
        }

        if (hasRecentActiveListing) {
            score = score + 20;
        }

        return score;
    }
}
