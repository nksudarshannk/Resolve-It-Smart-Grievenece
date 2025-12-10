package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "status_logs")
public class StatusLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "complaint_id")
    private Complaint complaint;
    
    private String status; // NEW, UNDER_REVIEW, RESOLVED
    
    @Column(length = 2000)
    private String comment;
    
    @Column(name = "updated_by")
    private String updatedBy; // Admin username
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Constructors
    public StatusLog() {
        this.updatedAt = LocalDateTime.now();
    }
    
    public StatusLog(Complaint complaint, String status, String comment, String updatedBy) {
        this.complaint = complaint;
        this.status = status;
        this.comment = comment;
        this.updatedBy = updatedBy;
        this.updatedAt = LocalDateTime.now();
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
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public String getUpdatedBy() {
        return updatedBy;
    }
    
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
