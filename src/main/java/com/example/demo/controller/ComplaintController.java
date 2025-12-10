package com.example.demo.controller;

import com.example.demo.entity.Complaint;
import com.example.demo.entity.ComplaintStatus;
import com.example.demo.entity.StatusUpdate;
import com.example.demo.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin(origins = "*")
public class ComplaintController {
    
    @Autowired
    private ComplaintService complaintService;
    
    @Autowired
    private com.example.demo.service.MediaUploadService mediaUploadService;
    
    @Autowired
    private com.example.demo.service.StatusLogService statusLogService;
    
    @PostMapping
    public ResponseEntity<?> createComplaint(@RequestBody Complaint complaint) {
        try {
            Complaint savedComplaint = complaintService.createComplaint(complaint);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Complaint submitted successfully");
            response.put("complaintId", savedComplaint.getComplaintId());
            response.put("complaint", savedComplaint);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error submitting complaint: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        List<Complaint> complaints = complaintService.getAllComplaints();
        return ResponseEntity.ok(complaints);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getComplaintById(@PathVariable Long id) {
        return complaintService.getComplaintById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/track/{complaintId}")
    public ResponseEntity<?> trackComplaint(@PathVariable String complaintId) {
        return complaintService.getComplaintByComplaintId(complaintId)
            .map(complaint -> {
                Map<String, Object> response = new HashMap<>();
                response.put("complaint", complaint);
                response.put("statusUpdates", complaintService.getStatusUpdates(complaint.getId()));
                return ResponseEntity.ok(response);
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Complaint>> getComplaintsByStatus(@PathVariable String status) {
        try {
            ComplaintStatus complaintStatus = ComplaintStatus.valueOf(status.toUpperCase());
            List<Complaint> complaints = complaintService.getComplaintsByStatus(complaintStatus);
            return ResponseEntity.ok(complaints);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<List<Complaint>> getComplaintsByEmail(@PathVariable String email) {
        List<Complaint> complaints = complaintService.getComplaintsByEmail(email);
        return ResponseEntity.ok(complaints);
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateComplaintStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> statusUpdate) {
        try {
            String newStatusStr = statusUpdate.get("status");
            String adminComment = statusUpdate.get("comment");
            String adminUsername = statusUpdate.getOrDefault("adminUsername", "admin");
            
            ComplaintStatus newStatus = ComplaintStatus.valueOf(newStatusStr.toUpperCase());
            
            Complaint updatedComplaint = complaintService.updateComplaintStatus(
                id, newStatus, adminComment, adminUsername
            );
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Status updated successfully");
            response.put("complaint", updatedComplaint);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Invalid status value");
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    @GetMapping("/{id}/timeline")
    public ResponseEntity<List<StatusUpdate>> getComplaintTimeline(@PathVariable Long id) {
        List<StatusUpdate> timeline = complaintService.getStatusUpdates(id);
        return ResponseEntity.ok(timeline);
    }
    
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", complaintService.getAllComplaints().size());
        stats.put("new", complaintService.getComplaintCountByStatus(ComplaintStatus.NEW));
        stats.put("underReview", complaintService.getComplaintCountByStatus(ComplaintStatus.UNDER_REVIEW));
        stats.put("resolved", complaintService.getComplaintCountByStatus(ComplaintStatus.RESOLVED));
        return ResponseEntity.ok(stats);
    }
    
    @PutMapping("/{id}/assign")
    public ResponseEntity<?> assignComplaint(
            @PathVariable Long id,
            @RequestBody Map<String, Object> assignment) {
        try {
            Long adminId = Long.valueOf(assignment.get("adminId").toString());
            String assignedBy = assignment.getOrDefault("assignedBy", "admin").toString();
            
            Complaint complaint = complaintService.assignComplaint(id, adminId, assignedBy);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Complaint assigned successfully");
            response.put("complaint", complaint);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    @GetMapping("/admin/{adminId}")
    public ResponseEntity<List<Complaint>> getComplaintsByAdmin(@PathVariable Long adminId) {
        return ResponseEntity.ok(complaintService.getComplaintsByAdmin(adminId));
    }
    
    @PutMapping("/{id}/priority")
    public ResponseEntity<?> updatePriority(
            @PathVariable Long id,
            @RequestBody Map<String, Object> update) {
        try {
            Integer priority = Integer.valueOf(update.get("priority").toString());
            String updatedBy = update.getOrDefault("updatedBy", "admin").toString();
            
            Complaint complaint = complaintService.updatePriority(id, priority, updatedBy);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Priority updated successfully");
            response.put("complaint", complaint);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    /**
     * MILESTONE 1: Upload media files for complaint
     */
    @PostMapping("/{id}/upload")
    public ResponseEntity<?> uploadMedia(
            @PathVariable Long id,
            @RequestParam("file") org.springframework.web.multipart.MultipartFile file) {
        try {
            com.example.demo.entity.MediaUpload media = mediaUploadService.uploadFile(id, file);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "File uploaded successfully");
            response.put("media", media);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    /**
     * MILESTONE 1: Get all media files for a complaint
     */
    @GetMapping("/{id}/media")
    public ResponseEntity<?> getComplaintMedia(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(mediaUploadService.getMediaByComplaint(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * MILESTONE 2: Get status timeline with detailed logs
     */
    @GetMapping("/{id}/status-logs")
    public ResponseEntity<?> getStatusLogs(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(statusLogService.getComplaintTimeline(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
