package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "escalations")
public class Escalation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id", nullable = false)
    private Complaint complaint;
    
    private String reason; // Why escalated
    private String escalatedBy; // Admin who escalated
    private String escalatedTo; // Senior admin username
    
    private LocalDateTime escalatedAt;
    private LocalDateTime resolvedAt;
    
    @Enumerated(EnumType.STRING)
    private EscalationStatus status; // PENDING, IN_PROGRESS, RESOLVED
    
    @Column(length = 1000)
    private String resolution;
    
    // Constructors
    public Escalation() {
        this.escalatedAt = LocalDateTime.now();
        this.status = EscalationStatus.PENDING;
    }
    
    public Escalation(Complaint complaint, String reason, String escalatedBy, String escalatedTo) {
        this.complaint = complaint;
        this.reason = reason;
        this.escalatedBy = escalatedBy;
        this.escalatedTo = escalatedTo;
        this.escalatedAt = LocalDateTime.now();
        this.status = EscalationStatus.PENDING;
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
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public String getEscalatedBy() {
        return escalatedBy;
    }
    
    public void setEscalatedBy(String escalatedBy) {
        this.escalatedBy = escalatedBy;
    }
    
    public String getEscalatedTo() {
        return escalatedTo;
    }
    
    public void setEscalatedTo(String escalatedTo) {
        this.escalatedTo = escalatedTo;
    }
    
    public LocalDateTime getEscalatedAt() {
        return escalatedAt;
    }
    
    public void setEscalatedAt(LocalDateTime escalatedAt) {
        this.escalatedAt = escalatedAt;
    }
    
    public LocalDateTime getResolvedAt() {
        return resolvedAt;
    }
    
    public void setResolvedAt(LocalDateTime resolvedAt) {
        this.resolvedAt = resolvedAt;
    }
    
    public EscalationStatus getStatus() {
        return status;
    }
    
    public void setStatus(EscalationStatus status) {
        this.status = status;
    }
    
    public String getResolution() {
        return resolution;
    }
    
    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
}
