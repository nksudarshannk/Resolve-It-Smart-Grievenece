package com.example.demo.service;

import com.example.demo.entity.Complaint;
import com.example.demo.entity.Escalation;
import com.example.demo.entity.EscalationStatus;
import com.example.demo.repository.ComplaintRepository;
import com.example.demo.repository.EscalationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EscalationService {
    
    @Autowired
    private EscalationRepository escalationRepository;
    
    @Autowired
    private ComplaintRepository complaintRepository;
    
    public Escalation escalateComplaint(Long complaintId, String reason, 
                                       String escalatedBy, String escalatedTo) {
        Complaint complaint = complaintRepository.findById(complaintId)
            .orElseThrow(() -> new RuntimeException("Complaint not found"));
        
        if (complaint.isEscalated()) {
            throw new RuntimeException("Complaint already escalated");
        }
        
        Escalation escalation = new Escalation(complaint, reason, escalatedBy, escalatedTo);
        escalation = escalationRepository.save(escalation);
        
        complaint.setEscalated(true);
        complaint.setEscalatedAt(LocalDateTime.now());
        complaintRepository.save(complaint);
        
        return escalation;
    }
    
    public List<Escalation> getAllEscalations() {
        return escalationRepository.findAll();
    }
    
    public List<Escalation> getEscalationsByStatus(EscalationStatus status) {
        return escalationRepository.findByStatus(status);
    }
    
    public List<Escalation> getEscalationsForAdmin(String adminUsername) {
        return escalationRepository.findByEscalatedTo(adminUsername);
    }
    
    public Escalation updateEscalationStatus(Long escalationId, EscalationStatus status, 
                                            String resolution) {
        Escalation escalation = escalationRepository.findById(escalationId)
            .orElseThrow(() -> new RuntimeException("Escalation not found"));
        
        escalation.setStatus(status);
        if (resolution != null) {
            escalation.setResolution(resolution);
        }
        if (status == EscalationStatus.RESOLVED) {
            escalation.setResolvedAt(LocalDateTime.now());
        }
        
        return escalationRepository.save(escalation);
    }
    
    // Auto-escalate complaints unresolved for X days
    public List<Escalation> autoEscalateOldComplaints(int daysThreshold, String escalatedTo) {
        List<Complaint> complaints = complaintRepository.findAll();
        List<Escalation> escalations = new java.util.ArrayList<>();
        
        LocalDateTime thresholdDate = LocalDateTime.now().minusDays(daysThreshold);
        
        for (Complaint complaint : complaints) {
            if (!complaint.isEscalated() && 
                complaint.getSubmittedAt().isBefore(thresholdDate) &&
                complaint.getStatus() != com.example.demo.entity.ComplaintStatus.RESOLVED) {
                
                Escalation escalation = escalateComplaint(
                    complaint.getId(),
                    "Auto-escalated: Unresolved for " + daysThreshold + " days",
                    "system",
                    escalatedTo
                );
                escalations.add(escalation);
            }
        }
        
        return escalations;
    }
}
