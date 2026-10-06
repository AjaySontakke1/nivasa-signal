package com.nivasasignal.source;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SourceListingScheduler {

    private final SourceListingService sourceListingService;

    @Scheduled(cron = "0 0 1 * * *")
    public void checkOldListings() {
        sourceListingService.markOldListingsInactive();
    }
}
