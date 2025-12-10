package com.example.demo.service;

import com.example.demo.entity.Complaint;
import com.example.demo.entity.StatusLog;
import com.example.demo.repository.ComplaintRepository;
import com.example.demo.repository.StatusLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StatusLogService {
    
    @Autowired
    private StatusLogRepository statusLogRepository;
    
    @Autowired
    private ComplaintRepository complaintRepository;
    
    /**
     * MILESTONE 2: Create status log entry
     */
    @Transactional
    public StatusLog createStatusLog(Long complaintId, String status, String comment, String updatedBy) {
        Complaint complaint = complaintRepository.findById(complaintId)
            .orElseThrow(() -> new RuntimeException("Complaint not found"));
        
        StatusLog statusLog = new StatusLog(complaint, status, comment, updatedBy);
        return statusLogRepository.save(statusLog);
    }
    
    /**
     * MILESTONE 2: Get timeline of all status changes for a complaint
     */
    public List<StatusLog> getComplaintTimeline(Long complaintId) {
        return statusLogRepository.findByComplaintIdOrderByUpdatedAtDesc(complaintId);
    }
    
    /**
     * Get all status logs
     */
    public List<StatusLog> getAllStatusLogs() {
        return statusLogRepository.findAll();
    }
}
