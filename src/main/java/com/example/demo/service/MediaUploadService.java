package com.example.demo.service;

import com.example.demo.entity.Complaint;
import com.example.demo.entity.MediaUpload;
import com.example.demo.repository.ComplaintRepository;
import com.example.demo.repository.MediaUploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class MediaUploadService {
    
    @Autowired
    private MediaUploadRepository mediaUploadRepository;
    
    @Autowired
    private ComplaintRepository complaintRepository;
    
    private final String UPLOAD_DIR = "uploads/complaints/";
    
    /**
     * MILESTONE 1: Upload media files with complaint
     */
    public MediaUpload uploadFile(Long complaintId, MultipartFile file) throws IOException {
        Complaint complaint = complaintRepository.findById(complaintId)
            .orElseThrow(() -> new RuntimeException("Complaint not found"));
        
        // Create upload directory if not exists
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        // Generate unique filename
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFilename = UUID.randomUUID().toString() + fileExtension;
        String filePath = UPLOAD_DIR + uniqueFilename;
        
        // Save file to disk
        Path targetPath = Paths.get(filePath);
        Files.write(targetPath, file.getBytes());
        
        // Create MediaUpload entity
        MediaUpload mediaUpload = new MediaUpload();
        mediaUpload.setComplaint(complaint);
        mediaUpload.setFilePath(filePath);
        mediaUpload.setFileName(originalFilename);
        mediaUpload.setFileSize(file.getSize());
        
        // Determine file type
        String contentType = file.getContentType();
        if (contentType != null) {
            if (contentType.startsWith("image/")) {
                mediaUpload.setFileType("IMAGE");
            } else if (contentType.startsWith("video/")) {
                mediaUpload.setFileType("VIDEO");
            } else {
                mediaUpload.setFileType("DOCUMENT");
            }
        }
        
        return mediaUploadRepository.save(mediaUpload);
    }
    
    /**
     * Get all media files for a complaint
     */
    public List<MediaUpload> getMediaByComplaint(Long complaintId) {
        return mediaUploadRepository.findByComplaintId(complaintId);
    }
    
    /**
     * Delete media file
     */
    public void deleteMedia(Long mediaId) throws IOException {
        MediaUpload media = mediaUploadRepository.findById(mediaId)
            .orElseThrow(() -> new RuntimeException("Media not found"));
        
        // Delete file from disk
        Path filePath = Paths.get(media.getFilePath());
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }
        
        // Delete from database
        mediaUploadRepository.delete(media);
    }
}
