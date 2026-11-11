package com.nivasasignal.service;

import com.nivasasignal.dto.PropertyStatusHistoryResponse;
import com.nivasasignal.entity.PropertyStatusHistory;
import com.nivasasignal.repository.PropertyStatusHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    public List<PropertyStatusHistoryResponse> getHistory(Long propertyId) {

        return propertyStatusHistoryRepository
                .findByPropertyIdOrderByChangedAtDesc(propertyId)
                .stream()
                .map(history -> new PropertyStatusHistoryResponse(
                        history.getId(),
                        history.getPropertyId(),
                        history.getAvailabilityStatus(),
                        history.getChangedAt()
                ))
                .toList();
    }
}
