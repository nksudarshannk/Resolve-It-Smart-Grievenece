package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
public class Report {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "generated_by")
    private Long generatedBy; // Admin ID
    
    @Column(name = "report_type")
    private String reportType; // SUMMARY, TREND, PERFORMANCE, EXPORT
    
    @Column(name = "report_path")
    private String reportPath;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    // Constructors
    public Report() {
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getGeneratedBy() {
        return generatedBy;
    }
    
    public void setGeneratedBy(Long generatedBy) {
        this.generatedBy = generatedBy;
    }
    
    public String getReportType() {
        return reportType;
    }
    
    public void setReportType(String reportType) {
        this.reportType = reportType;
    }
    
    public String getReportPath() {
        return reportPath;
    }
    
    public void setReportPath(String reportPath) {
        this.reportPath = reportPath;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
