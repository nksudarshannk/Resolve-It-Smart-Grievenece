package com.example.demo.service;

import com.example.demo.entity.Complaint;
import com.example.demo.entity.ComplaintStatus;
import com.example.demo.entity.StatusUpdate;
import com.example.demo.repository.ComplaintRepository;
import com.example.demo.repository.StatusUpdateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.Optional;

@Service
public class ComplaintService {
    
    @Autowired
    private ComplaintRepository complaintRepository;
    
    @Autowired
    private StatusUpdateRepository statusUpdateRepository;
    
    @Autowired
    private com.example.demo.repository.AdminRepository adminRepository;
    
    @Autowired
    private com.example.demo.service.StatusLogService statusLogService;
    
    @Transactional
    public Complaint createComplaint(Complaint complaint) {
        // Generate unique complaint ID
        String complaintId = generateComplaintId();
        complaint.setComplaintId(complaintId);
        complaint.setStatus(ComplaintStatus.NEW);
        complaint.setSubmittedAt(LocalDateTime.now());
        complaint.setLastUpdatedAt(LocalDateTime.now());
        
        // Save complaint
        Complaint savedComplaint = complaintRepository.save(complaint);
        
        // Create initial status update
        StatusUpdate initialUpdate = new StatusUpdate(
            null, 
            ComplaintStatus.NEW, 
            "Complaint submitted successfully", 
            "System"
        );
        initialUpdate.setComplaint(savedComplaint);
        statusUpdateRepository.save(initialUpdate);
        
        // MILESTONE 2: Create initial status log
        statusLogService.createStatusLog(
            savedComplaint.getId(),
            "NEW",
            "Complaint submitted successfully",
            "System"
        );
        
        return savedComplaint;
    }
    
    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAllByOrderBySubmittedAtDesc();
    }
    
    public Optional<Complaint> getComplaintById(Long id) {
        return complaintRepository.findById(id);
    }
    
    public Optional<Complaint> getComplaintByComplaintId(String complaintId) {
        return complaintRepository.findByComplaintId(complaintId);
    }
    
    public List<Complaint> getComplaintsByStatus(ComplaintStatus status) {
        return complaintRepository.findByStatus(status);
    }
    
    public List<Complaint> getComplaintsByEmail(String email) {
        return complaintRepository.findByEmailOrderBySubmittedAtDesc(email);
    }
    
    @Transactional
    public Complaint updateComplaintStatus(Long complaintId, ComplaintStatus newStatus, String adminComment, String adminUsername) {
        Optional<Complaint> optComplaint = complaintRepository.findById(complaintId);
        
        if (optComplaint.isEmpty()) {
            throw new RuntimeException("Complaint not found with id: " + complaintId);
        }
        
        Complaint complaint = optComplaint.get();
        ComplaintStatus oldStatus = complaint.getStatus();
        
        // Validate status transition
        if (!isValidStatusTransition(oldStatus, newStatus)) {
            throw new RuntimeException("Invalid status transition from " + oldStatus + " to " + newStatus);
        }
        
        // Update complaint status
        complaint.setStatus(newStatus);
        complaint.setLastUpdatedAt(LocalDateTime.now());
        
        // Create status update record
        StatusUpdate statusUpdate = new StatusUpdate(
            oldStatus,
            newStatus,
            adminComment,
            adminUsername
        );
        statusUpdate.setComplaint(complaint);
        
        statusUpdateRepository.save(statusUpdate);
        
        // MILESTONE 2: Create status log
        statusLogService.createStatusLog(
            complaint.getId(),
            newStatus.toString(),
            adminComment,
            adminUsername
        );
        
        return complaintRepository.save(complaint);
    }
    
    public List<StatusUpdate> getStatusUpdates(Long complaintId) {
        return statusUpdateRepository.findByComplaintIdOrderByTimestampDesc(complaintId);
    }
    
    private boolean isValidStatusTransition(ComplaintStatus from, ComplaintStatus to) {
        // NEW can go to UNDER_REVIEW or RESOLVED
        if (from == ComplaintStatus.NEW) {
            return to == ComplaintStatus.UNDER_REVIEW || to == ComplaintStatus.RESOLVED;
        }
        // UNDER_REVIEW can go to RESOLVED or back to NEW
        if (from == ComplaintStatus.UNDER_REVIEW) {
            return to == ComplaintStatus.RESOLVED || to == ComplaintStatus.NEW;
        }
        // RESOLVED can be reopened to UNDER_REVIEW or NEW
        if (from == ComplaintStatus.RESOLVED) {
            return to == ComplaintStatus.UNDER_REVIEW || to == ComplaintStatus.NEW;
        }
        return false;
    }
    
    private String generateComplaintId() {
        long count = complaintRepository.count() + 1;
        int year = Year.now().getValue();
        return String.format("CMP-%d-%03d", year, count);
    }
    
    public long getComplaintCountByStatus(ComplaintStatus status) {
        return complaintRepository.findByStatus(status).size();
    }
    
    // Assign complaint to admin
    @Transactional
    public Complaint assignComplaint(Long complaintId, Long adminId, String assignedBy) {
        Complaint complaint = complaintRepository.findById(complaintId)
            .orElseThrow(() -> new RuntimeException("Complaint not found"));
        
        com.example.demo.entity.Admin admin = adminRepository.findById(adminId)
            .orElseThrow(() -> new RuntimeException("Admin not found"));
        
        complaint.setAssignedTo(admin);
        complaint.setLastUpdatedAt(LocalDateTime.now());
        
        // Create status update
        StatusUpdate update = new StatusUpdate(
            complaint.getStatus(),
            complaint.getStatus(),
            "Assigned to " + admin.getFullName(),
            assignedBy
        );
        update.setComplaint(complaint);
        statusUpdateRepository.save(update);
        
        return complaintRepository.save(complaint);
    }
    
    // Get complaints assigned to specific admin
    public List<Complaint> getComplaintsByAdmin(Long adminId) {
        return complaintRepository.findByAssignedToId(adminId);
    }
    
    // Update priority
    @Transactional
    public Complaint updatePriority(Long complaintId, Integer priority, String updatedBy) {
        Complaint complaint = complaintRepository.findById(complaintId)
            .orElseThrow(() -> new RuntimeException("Complaint not found"));
        
        complaint.setPriority(priority);
        complaint.setLastUpdatedAt(LocalDateTime.now());
        
        // Create status update
        StatusUpdate update = new StatusUpdate(
            complaint.getStatus(),
            complaint.getStatus(),
            "Priority updated to " + priority,
            updatedBy
        );
        update.setComplaint(complaint);
        statusUpdateRepository.save(update);
        
        return complaintRepository.save(complaint);
    }
}
