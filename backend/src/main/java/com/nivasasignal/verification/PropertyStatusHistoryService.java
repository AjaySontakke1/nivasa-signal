package com.nivasasignal.verification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PropertyStatusHistoryService {

    private final PropertyStatusHistoryRepository propertyStatusHistoryRepository;

    public void saveStatus(Long propertyId, String availabilityStatus) {
        PropertyStatusHistory history = new PropertyStatusHistory();

        history.setPropertyId(propertyId);
        history.setAvailabilityStatus(availabilityStatus);
        history.setChangedAt(LocalDateTime.now());

        propertyStatusHistoryRepository.save(history);
    }
}
