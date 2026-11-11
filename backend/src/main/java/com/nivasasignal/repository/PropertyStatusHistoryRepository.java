package com.nivasasignal.repository;

import com.nivasasignal.entity.PropertyStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyStatusHistoryRepository
        extends JpaRepository<PropertyStatusHistory, Long> {

    List<PropertyStatusHistory> findByPropertyIdOrderByChangedAtDesc(
            Long propertyId
    );
}
