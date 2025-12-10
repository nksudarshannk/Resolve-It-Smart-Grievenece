package com.example.demo.repository;

import com.example.demo.entity.Escalation;
import com.example.demo.entity.EscalationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EscalationRepository extends JpaRepository<Escalation, Long> {
    Optional<Escalation> findByComplaintId(Long complaintId);
    List<Escalation> findByStatus(EscalationStatus status);
    List<Escalation> findByEscalatedTo(String escalatedTo);
    List<Escalation> findByEscalatedBy(String escalatedBy);
}
