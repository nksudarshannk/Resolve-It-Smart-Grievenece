package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "status_updates")
public class StatusUpdate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id", nullable = false)
    private Complaint complaint;
    
    @Enumerated(EnumType.STRING)
    private ComplaintStatus fromStatus;
    
    @Enumerated(EnumType.STRING)
    private ComplaintStatus toStatus;
    
    @Column(length = 1000)
    private String adminComment;
    
    private String updatedBy; // Admin username
    
    private LocalDateTime timestamp;
    
    // Constructors
    public StatusUpdate() {
        this.timestamp = LocalDateTime.now();
    }
    
    public StatusUpdate(ComplaintStatus fromStatus, ComplaintStatus toStatus, String adminComment, String updatedBy) {
        this.fromStatus = fromStatus;
        this.toStatus = toStatus;
        this.adminComment = adminComment;
        this.updatedBy = updatedBy;
        this.timestamp = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Complaint getComplaint() {
        return complaint;
    }
    
    public void setComplaint(Complaint complaint) {
        this.complaint = complaint;
    }
    
    public ComplaintStatus getFromStatus() {
        return fromStatus;
    }
    
    public void setFromStatus(ComplaintStatus fromStatus) {
        this.fromStatus = fromStatus;
    }
    
    public ComplaintStatus getToStatus() {
        return toStatus;
    }
    
    public void setToStatus(ComplaintStatus toStatus) {
        this.toStatus = toStatus;
    }
    
    public String getAdminComment() {
        return adminComment;
    }
    
    public void setAdminComment(String adminComment) {
        this.adminComment = adminComment;
    }
    
    public String getUpdatedBy() {
        return updatedBy;
    }
    
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
