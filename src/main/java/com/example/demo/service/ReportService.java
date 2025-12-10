package com.example.demo.service;

import com.example.demo.entity.Complaint;
import com.example.demo.entity.ComplaintStatus;
import com.example.demo.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {
    
    @Autowired
    private ComplaintRepository complaintRepository;
    
    // Generate Dashboard Statistics
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        List<Complaint> allComplaints = complaintRepository.findAll();
        
        // Status Distribution
        Map<String, Long> statusCounts = allComplaints.stream()
            .collect(Collectors.groupingBy(
                c -> c.getStatus().toString(),
                Collectors.counting()
            ));
        stats.put("statusDistribution", statusCounts);
        
        // Category Distribution
        Map<String, Long> categoryCounts = allComplaints.stream()
            .collect(Collectors.groupingBy(
                Complaint::getCategory,
                Collectors.counting()
            ));
        stats.put("categoryDistribution", categoryCounts);
        
        // Urgency Distribution
        Map<String, Long> urgencyCounts = allComplaints.stream()
            .collect(Collectors.groupingBy(
                Complaint::getUrgency,
                Collectors.counting()
            ));
        stats.put("urgencyDistribution", urgencyCounts);
        
        // Total Counts
        stats.put("totalComplaints", allComplaints.size());
        stats.put("totalNew", statusCounts.getOrDefault("NEW", 0L));
        stats.put("totalUnderReview", statusCounts.getOrDefault("UNDER_REVIEW", 0L));
        stats.put("totalResolved", statusCounts.getOrDefault("RESOLVED", 0L));
        
        // Escalated Complaints
        long escalatedCount = allComplaints.stream()
            .filter(Complaint::isEscalated)
            .count();
        stats.put("totalEscalated", escalatedCount);
        
        // Average Resolution Time (for resolved complaints)
        double avgResolutionDays = allComplaints.stream()
            .filter(c -> c.getStatus() == ComplaintStatus.RESOLVED)
            .mapToLong(c -> java.time.Duration.between(
                c.getSubmittedAt(), 
                c.getLastUpdatedAt()
            ).toDays())
            .average()
            .orElse(0.0);
        stats.put("averageResolutionDays", Math.round(avgResolutionDays * 10) / 10.0);
        
        return stats;
    }
    
    // Get Trend Data for Charts
    public Map<String, Object> getTrendData(int days) {
        Map<String, Object> trendData = new HashMap<>();
        LocalDateTime startDate = LocalDateTime.now().minusDays(days);
        
        List<Complaint> recentComplaints = complaintRepository.findAll().stream()
            .filter(c -> c.getSubmittedAt().isAfter(startDate))
            .collect(Collectors.toList());
        
        // Daily submission count
        Map<String, Long> dailySubmissions = recentComplaints.stream()
            .collect(Collectors.groupingBy(
                c -> c.getSubmittedAt().toLocalDate().toString(),
                Collectors.counting()
            ));
        trendData.put("dailySubmissions", dailySubmissions);
        
        return trendData;
    }
    
    // Export Data for CSV/PDF
    public List<Map<String, Object>> getExportData(String filterType, String filterValue) {
        List<Complaint> complaints;
        
        switch (filterType != null ? filterType.toLowerCase() : "all") {
            case "status":
                complaints = complaintRepository.findByStatus(
                    ComplaintStatus.valueOf(filterValue.toUpperCase())
                );
                break;
            case "category":
                complaints = complaintRepository.findByCategory(filterValue);
                break;
            case "escalated":
                complaints = complaintRepository.findByIsEscalatedTrue();
                break;
            default:
                complaints = complaintRepository.findAll();
        }
        
        return complaints.stream()
            .map(this::convertToExportMap)
            .collect(Collectors.toList());
    }
    
    private Map<String, Object> convertToExportMap(Complaint complaint) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("Complaint ID", complaint.getComplaintId());
        map.put("Name", complaint.getName());
        map.put("Email", complaint.getEmail());
        map.put("Category", complaint.getCategory());
        map.put("Urgency", complaint.getUrgency());
        map.put("Subject", complaint.getSubject());
        map.put("Status", complaint.getStatus().toString());
        map.put("Submitted At", complaint.getSubmittedAt().toString());
        map.put("Last Updated", complaint.getLastUpdatedAt().toString());
        map.put("Is Escalated", complaint.isEscalated() ? "Yes" : "No");
        map.put("Assigned To", complaint.getAssignedTo() != null ? 
            complaint.getAssignedTo().getFullName() : "Unassigned");
        return map;
    }
    
    // Get Performance Metrics
    public Map<String, Object> getPerformanceMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        
        List<Complaint> allComplaints = complaintRepository.findAll();
        
        // Resolution Rate
        long total = allComplaints.size();
        long resolved = allComplaints.stream()
            .filter(c -> c.getStatus() == ComplaintStatus.RESOLVED)
            .count();
        double resolutionRate = total > 0 ? (resolved * 100.0 / total) : 0;
        metrics.put("resolutionRate", Math.round(resolutionRate * 10) / 10.0);
        
        // Escalation Rate
        long escalated = allComplaints.stream()
            .filter(Complaint::isEscalated)
            .count();
        double escalationRate = total > 0 ? (escalated * 100.0 / total) : 0;
        metrics.put("escalationRate", Math.round(escalationRate * 10) / 10.0);
        
        // Pending Complaints by Age
        Map<String, Long> pendingByAge = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();
        
        allComplaints.stream()
            .filter(c -> c.getStatus() != ComplaintStatus.RESOLVED)
            .forEach(c -> {
                long daysOld = java.time.Duration.between(c.getSubmittedAt(), now).toDays();
                String ageGroup;
                if (daysOld < 1) ageGroup = "< 1 day";
                else if (daysOld < 7) ageGroup = "1-7 days";
                else if (daysOld < 30) ageGroup = "1-4 weeks";
                else ageGroup = "> 1 month";
                
                pendingByAge.merge(ageGroup, 1L, Long::sum);
            });
        metrics.put("pendingByAge", pendingByAge);
        
        return metrics;
    }
}
