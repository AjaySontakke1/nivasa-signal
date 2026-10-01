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

        if (listings.size() >= 1) {
            score = score + 30;
        }

        if (listings.size() >= 2) {
            score = score + 20;
        }

        boolean checkedToday = false;

        for (SourceListing listing : listings) {
            if (listing.getLastSeenAt() != null
                    && listing.getLastSeenAt().isAfter(LocalDateTime.now().minusDays(1))) {
                checkedToday = true;
            }
        }

        if (checkedToday) {
            score = score + 20;
        }

        return score;
    }
}
