package com.nivasasignal.source.connector;

import com.nivasasignal.config.CsvImportProperties;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.Reader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CsvPartnerConnector implements SourceConnector {

    private final CsvImportProperties csvImportProperties;

    @Override
    public String sourceName() {
        return "CSV_PARTNER";
    }

    @Override
    public List<ExternalListing> fetchListings(String city) {

        String filePath = csvImportProperties.getPartnerFile();

        if (filePath == null || filePath.isBlank()) {
            return List.of();
        }

        Path path = Path.of(filePath);
        if (!Files.exists(path)) {
            return List.of();
        }

        List<ExternalListing> listings = new ArrayList<>();

        try (
                Reader reader = Files.newBufferedReader(path);
                CSVParser csvParser = CSVParser.parse(
                        reader,
                        CSVFormat.DEFAULT.builder()
                                .setHeader()
                                .setSkipHeaderRecord(true)
                                .get()
                )
        ) {
            for (CSVRecord record : csvParser) {

                String listingCity = getText(record, "city");

                if (city != null && !city.isBlank() && !city.equalsIgnoreCase(listingCity)) {
                    continue;
                }

                ExternalListing listing = new ExternalListing(
                        getText(record, "external_listing_id"),
                        getText(record, "title"),
                        listingCity,
                        getText(record, "locality"),
                        getText(record, "source_url"),
                        getText(record, "property_type"),
                        getText(record, "bhk"),
                        getDecimal(record, "area_sqft"),
                        getLong(record, "price_inr"),
                        getText(record, "status"),
                        LocalDateTime.now()
                );

                listings.add(listing);
            }

        } catch (Exception exception) {
            throw new RuntimeException(
                    "Could not read partner CSV file",
                    exception
            );
        }

        return listings;
    }

    private String getText(CSVRecord record, String columnName) {
        String value = record.get(columnName);

        if (value == null || value.isBlank()) {
            return null;
        }

        return value.trim();
    }

    private Long getLong(CSVRecord record, String columnName) {
        String value = getText(record, columnName);

        if (value == null) {
            return null;
        }

        return Long.parseLong(value);
    }

    private BigDecimal getDecimal(
            CSVRecord record,
            String columnName
    ) {
        String value = getText(record, columnName);

        if (value == null) {
            return null;
        }

        return new BigDecimal(value);
    }
}
