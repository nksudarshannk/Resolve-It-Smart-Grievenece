package com.example.demo.controller;

import com.example.demo.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {
    
    @Autowired
    private ReportService reportService;
    
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        return ResponseEntity.ok(reportService.getDashboardStats());
    }
    
    @GetMapping("/trends")
    public ResponseEntity<Map<String, Object>> getTrendData(@RequestParam(defaultValue = "30") int days) {
        return ResponseEntity.ok(reportService.getTrendData(days));
    }
    
    @GetMapping("/performance")
    public ResponseEntity<Map<String, Object>> getPerformanceMetrics() {
        return ResponseEntity.ok(reportService.getPerformanceMetrics());
    }
    
    @GetMapping("/export/csv")
    public ResponseEntity<String> exportToCSV(
            @RequestParam(required = false) String filterType,
            @RequestParam(required = false) String filterValue) {
        
        List<Map<String, Object>> data = reportService.getExportData(filterType, filterValue);
        
        if (data.isEmpty()) {
            return ResponseEntity.ok("No data to export");
        }
        
        // Generate CSV
        StringBuilder csv = new StringBuilder();
        
        // Headers
        csv.append(String.join(",", data.get(0).keySet())).append("\n");
        
        // Data rows
        for (Map<String, Object> row : data) {
            csv.append(row.values().stream()
                .map(v -> "\"" + (v != null ? v.toString().replace("\"", "\"\"") : "") + "\"")
                .collect(Collectors.joining(",")))
                .append("\n");
        }
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.setContentDispositionFormData("attachment", "complaints_report.csv");
        
        return ResponseEntity.ok()
            .headers(headers)
            .body(csv.toString());
    }
    
    @GetMapping("/export/json")
    public ResponseEntity<List<Map<String, Object>>> exportToJSON(
            @RequestParam(required = false) String filterType,
            @RequestParam(required = false) String filterValue) {
        
        List<Map<String, Object>> data = reportService.getExportData(filterType, filterValue);
        return ResponseEntity.ok(data);
    }
}
