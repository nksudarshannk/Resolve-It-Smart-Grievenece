package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "complaints")
public class Complaint {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String complaintId; // Unique tracking ID like "CMP-2024-001"
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // Linked user (null if anonymous)
    
    private boolean isAnonymous; // True for anonymous submissions
    
    private String name;
    private String email;
    private String phone;
    private String category;
    private String urgency;
    private String subject;
    
    @Column(length = 2000)
    private String description;
    
    @Enumerated(EnumType.STRING)
    private ComplaintStatus status;
    
    private LocalDateTime submittedAt;
    private LocalDateTime lastUpdatedAt;
    
    @OneToMany(mappedBy = "complaint", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StatusUpdate> statusUpdates = new ArrayList<>();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to")
    private Admin assignedTo;
    
    @OneToMany(mappedBy = "complaint", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InternalNote> internalNotes = new ArrayList<>();
    
    @OneToMany(mappedBy = "complaint", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserReply> userReplies = new ArrayList<>();
    
    @OneToMany(mappedBy = "complaint", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MediaUpload> mediaUploads = new ArrayList<>();
    
    @OneToMany(mappedBy = "complaint", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StatusLog> statusLogs = new ArrayList<>();
    
    @OneToOne(mappedBy = "complaint", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Escalation escalation;
    
    private Integer priority; // 1-5, 5 being highest
    private LocalDateTime escalatedAt;
    private boolean isEscalated = false;
    private Integer daysUnresolved;
    
    // Constructors
    public Complaint() {
        this.submittedAt = LocalDateTime.now();
        this.lastUpdatedAt = LocalDateTime.now();
        this.status = ComplaintStatus.NEW;
        this.priority = 3; // Default medium priority
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getComplaintId() {
        return complaintId;
    }
    
    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getUrgency() {
        return urgency;
    }
    
    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }
    
    public String getSubject() {
        return subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public boolean isAnonymous() {
        return isAnonymous;
    }
    
    public void setAnonymous(boolean anonymous) {
        this.isAnonymous = anonymous;
    }
    
    public ComplaintStatus getStatus() {
        return status;
    }
    
    public void setStatus(ComplaintStatus status) {
        this.status = status;
        this.lastUpdatedAt = LocalDateTime.now();
    }
    
    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }
    
    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
    
    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }
    
    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }
    
    public List<StatusUpdate> getStatusUpdates() {
        return statusUpdates;
    }
    
    public void setStatusUpdates(List<StatusUpdate> statusUpdates) {
        this.statusUpdates = statusUpdates;
    }
    
    public void addStatusUpdate(StatusUpdate update) {
        statusUpdates.add(update);
        update.setComplaint(this);
    }
    
    public Admin getAssignedTo() {
        return assignedTo;
    }
    
    public void setAssignedTo(Admin assignedTo) {
        this.assignedTo = assignedTo;
    }
    
    public List<InternalNote> getInternalNotes() {
        return internalNotes;
    }
    
    public void setInternalNotes(List<InternalNote> internalNotes) {
        this.internalNotes = internalNotes;
    }
    
    public void addInternalNote(InternalNote note) {
        internalNotes.add(note);
        note.setComplaint(this);
    }
    
    public List<UserReply> getUserReplies() {
        return userReplies;
    }
    
    public void setUserReplies(List<UserReply> userReplies) {
        this.userReplies = userReplies;
    }
    
    public void addUserReply(UserReply reply) {
        userReplies.add(reply);
        reply.setComplaint(this);
    }
    
    public List<MediaUpload> getMediaUploads() {
        return mediaUploads;
    }
    
    public void setMediaUploads(List<MediaUpload> mediaUploads) {
        this.mediaUploads = mediaUploads;
    }
    
    public void addMediaUpload(MediaUpload media) {
        mediaUploads.add(media);
        media.setComplaint(this);
    }
    
    public List<StatusLog> getStatusLogs() {
        return statusLogs;
    }
    
    public void setStatusLogs(List<StatusLog> statusLogs) {
        this.statusLogs = statusLogs;
    }
    
    public void addStatusLog(StatusLog log) {
        statusLogs.add(log);
        log.setComplaint(this);
    }
    
    public Escalation getEscalation() {
        return escalation;
    }
    
    public void setEscalation(Escalation escalation) {
        this.escalation = escalation;
    }
    
    public Integer getPriority() {
        return priority;
    }
    
    public void setPriority(Integer priority) {
        this.priority = priority;
    }
    
    public LocalDateTime getEscalatedAt() {
        return escalatedAt;
    }
    
    public void setEscalatedAt(LocalDateTime escalatedAt) {
        this.escalatedAt = escalatedAt;
    }
    
    public boolean isEscalated() {
        return isEscalated;
    }
    
    public void setEscalated(boolean escalated) {
        isEscalated = escalated;
    }
    
    public Integer getDaysUnresolved() {
        return daysUnresolved;
    }
    
    public void setDaysUnresolved(Integer daysUnresolved) {
        this.daysUnresolved = daysUnresolved;
    }
}
