package com.nivasasignal.source;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SourceListingRepository
        extends JpaRepository<SourceListing, Long> {

    List<SourceListing> findByPropertyId(Long propertyId);
}
