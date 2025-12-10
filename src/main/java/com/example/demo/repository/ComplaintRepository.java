package com.example.demo.repository;

import com.example.demo.entity.Complaint;
import com.example.demo.entity.ComplaintStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    
    Optional<Complaint> findByComplaintId(String complaintId);
    
    List<Complaint> findByStatus(ComplaintStatus status);
    
    List<Complaint> findByEmailOrderBySubmittedAtDesc(String email);
    
    List<Complaint> findAllByOrderBySubmittedAtDesc();
    
    List<Complaint> findByAssignedToId(Long adminId);
    
    List<Complaint> findByIsEscalatedTrue();
    
    List<Complaint> findByCategory(String category);
    
    List<Complaint> findByUrgency(String urgency);
    
    Long countByStatus(ComplaintStatus status);
    
    Long countByCategory(String category);
}
