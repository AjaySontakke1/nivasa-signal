package com.nivasasignal.source.connector;

import java.util.List;

public interface SourceConnector {

    String sourceName();

    List<ExternalListing> fetchListings(String city);
}
