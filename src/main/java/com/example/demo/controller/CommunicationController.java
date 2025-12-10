package com.example.demo.controller;

import com.example.demo.service.CommunicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/communication")
@CrossOrigin(origins = "*")
public class CommunicationController {
    
    @Autowired
    private CommunicationService communicationService;
    
    // Internal Notes
    @PostMapping("/notes")
    public ResponseEntity<?> addInternalNote(@RequestBody Map<String, Object> request) {
        Long complaintId = Long.valueOf(request.get("complaintId").toString());
        String note = request.get("note").toString();
        String createdBy = request.get("createdBy").toString();
        
        return ResponseEntity.ok(
            communicationService.addInternalNote(complaintId, note, createdBy)
        );
    }
    
    @GetMapping("/notes/{complaintId}")
    public ResponseEntity<?> getInternalNotes(@PathVariable Long complaintId) {
        return ResponseEntity.ok(communicationService.getInternalNotes(complaintId));
    }
    
    // User Replies
    @PostMapping("/replies")
    public ResponseEntity<?> addUserReply(@RequestBody Map<String, Object> request) {
        Long complaintId = Long.valueOf(request.get("complaintId").toString());
        String message = request.get("message").toString();
        String repliedBy = request.get("repliedBy").toString();
        
        return ResponseEntity.ok(
            communicationService.addUserReply(complaintId, message, repliedBy)
        );
    }
    
    @GetMapping("/replies/{complaintId}")
    public ResponseEntity<?> getUserReplies(@PathVariable Long complaintId) {
        return ResponseEntity.ok(communicationService.getUserReplies(complaintId));
    }
}
