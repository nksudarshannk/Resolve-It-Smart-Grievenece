package com.example.demo.entity;

public enum ComplaintStatus {
    NEW("New", "Complaint has been submitted and is awaiting review"),
    UNDER_REVIEW("Under Review", "Complaint is being reviewed by an administrator"),
    RESOLVED("Resolved", "Complaint has been resolved");
    
    private final String displayName;
    private final String description;
    
    ComplaintStatus(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getDescription() {
        return description;
    }
}
