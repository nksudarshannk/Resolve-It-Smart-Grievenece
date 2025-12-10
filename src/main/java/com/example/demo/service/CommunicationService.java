package com.example.demo.service;

import com.example.demo.entity.InternalNote;
import com.example.demo.entity.UserReply;
import com.example.demo.repository.InternalNoteRepository;
import com.example.demo.repository.UserReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunicationService {
    
    @Autowired
    private InternalNoteRepository internalNoteRepository;
    
    @Autowired
    private UserReplyRepository userReplyRepository;
    
    // Internal Notes (Private - Staff Only)
    public InternalNote addInternalNote(Long complaintId, String note, String createdBy) {
        InternalNote internalNote = new InternalNote(note, createdBy);
        internalNote.setComplaint(
            new com.example.demo.entity.Complaint()
        );
        internalNote.getComplaint().setId(complaintId);
        return internalNoteRepository.save(internalNote);
    }
    
    public List<InternalNote> getInternalNotes(Long complaintId) {
        return internalNoteRepository.findByComplaintIdOrderByCreatedAtDesc(complaintId);
    }
    
    // User Replies (Public - Visible to Users)
    public UserReply addUserReply(Long complaintId, String message, String repliedBy) {
        UserReply reply = new UserReply(message, repliedBy);
        reply.setComplaint(new com.example.demo.entity.Complaint());
        reply.getComplaint().setId(complaintId);
        return userReplyRepository.save(reply);
    }
    
    public List<UserReply> getUserReplies(Long complaintId) {
        return userReplyRepository.findByComplaintIdOrderByRepliedAtDesc(complaintId);
    }
    
    public UserReply markReplySent(Long replyId) {
        UserReply reply = userReplyRepository.findById(replyId)
            .orElseThrow(() -> new RuntimeException("Reply not found"));
        reply.setSentToUser(true);
        return userReplyRepository.save(reply);
    }
}
