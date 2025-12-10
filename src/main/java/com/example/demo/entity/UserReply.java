package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_replies")
public class UserReply {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id", nullable = false)
    private Complaint complaint;
    
    @Column(length = 2000, nullable = false)
    private String message;
    
    private String repliedBy; // Admin username
    private LocalDateTime repliedAt;
    
    private boolean sentToUser = false; // Email notification sent?
    
    // Constructors
    public UserReply() {
        this.repliedAt = LocalDateTime.now();
    }
    
    public UserReply(String message, String repliedBy) {
        this.message = message;
        this.repliedBy = repliedBy;
        this.repliedAt = LocalDateTime.now();
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
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getRepliedBy() {
        return repliedBy;
    }
    
    public void setRepliedBy(String repliedBy) {
        this.repliedBy = repliedBy;
    }
    
    public LocalDateTime getRepliedAt() {
        return repliedAt;
    }
    
    public void setRepliedAt(LocalDateTime repliedAt) {
        this.repliedAt = repliedAt;
    }
    
    public boolean isSentToUser() {
        return sentToUser;
    }
    
    public void setSentToUser(boolean sentToUser) {
        this.sentToUser = sentToUser;
    }
}
