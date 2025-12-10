package com.example.demo.controller;

import com.example.demo.entity.Escalation;
import com.example.demo.entity.EscalationStatus;
import com.example.demo.service.EscalationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/escalations")
@CrossOrigin(origins = "*")
public class EscalationController {
    
    @Autowired
    private EscalationService escalationService;
    
    @PostMapping
    public ResponseEntity<Escalation> escalateComplaint(@RequestBody Map<String, Object> request) {
        Long complaintId = Long.valueOf(request.get("complaintId").toString());
        String reason = request.get("reason").toString();
        String escalatedBy = request.get("escalatedBy").toString();
        String escalatedTo = request.get("escalatedTo").toString();
        
        Escalation escalation = escalationService.escalateComplaint(
            complaintId, reason, escalatedBy, escalatedTo
        );
        return ResponseEntity.ok(escalation);
    }
    
    @GetMapping
    public ResponseEntity<List<Escalation>> getAllEscalations() {
        return ResponseEntity.ok(escalationService.getAllEscalations());
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Escalation>> getEscalationsByStatus(@PathVariable String status) {
        EscalationStatus escalationStatus = EscalationStatus.valueOf(status.toUpperCase());
        return ResponseEntity.ok(escalationService.getEscalationsByStatus(escalationStatus));
    }
    
    @GetMapping("/admin/{username}")
    public ResponseEntity<List<Escalation>> getEscalationsForAdmin(@PathVariable String username) {
        return ResponseEntity.ok(escalationService.getEscalationsForAdmin(username));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Escalation> updateEscalation(
            @PathVariable Long id, 
            @RequestBody Map<String, String> update) {
        
        EscalationStatus status = EscalationStatus.valueOf(update.get("status").toUpperCase());
        String resolution = update.get("resolution");
        
        return ResponseEntity.ok(
            escalationService.updateEscalationStatus(id, status, resolution)
        );
    }
    
    @PostMapping("/auto-escalate")
    public ResponseEntity<List<Escalation>> autoEscalate(@RequestBody Map<String, Object> request) {
        int daysThreshold = Integer.parseInt(request.get("daysThreshold").toString());
        String escalatedTo = request.get("escalatedTo").toString();
        
        List<Escalation> escalations = escalationService.autoEscalateOldComplaints(
            daysThreshold, escalatedTo
        );
        return ResponseEntity.ok(escalations);
    }
}
