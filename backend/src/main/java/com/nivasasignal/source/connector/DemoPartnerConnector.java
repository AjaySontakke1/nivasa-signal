package com.nivasasignal.source.connector;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DemoPartnerConnector implements SourceConnector {

    @Override
    public String sourceName() {
        return "DEMO_PARTNER";
    }

    @Override
    public List<ExternalListing> fetchListings(String city) {

        ExternalListing listing = new ExternalListing(
                "DEMO-1001",
                "2 BHK Flat in Wakad",
                city,
                "Wakad",
                "https://example.com/demo/DEMO-1001",
                "FLAT",
                "2BHK",
                new BigDecimal("950"),
                7500000L,
                "AVAILABLE",
                LocalDateTime.now()
        );

        return List.of(listing);
    }
}
