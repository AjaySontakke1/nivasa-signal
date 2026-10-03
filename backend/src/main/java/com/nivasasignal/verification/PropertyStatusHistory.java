package com.nivasasignal.verification;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "property_status_history")
@Getter
@Setter
@NoArgsConstructor
public class PropertyStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "property_id")
    private Long propertyId;

    @Column(name = "availability_status")
    private String availabilityStatus;

    @Column(name = "changed_at")
    private LocalDateTime changedAt;
}
