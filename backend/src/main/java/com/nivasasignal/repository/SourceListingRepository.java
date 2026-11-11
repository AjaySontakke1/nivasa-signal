package com.nivasasignal.repository;

import com.nivasasignal.entity.SourceListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SourceListingRepository
        extends JpaRepository<SourceListing, Long> {

    List<SourceListing> findByPropertyId(Long propertyId);

    List<SourceListing> findByLastSeenAtBefore(LocalDateTime dateTime);
}
