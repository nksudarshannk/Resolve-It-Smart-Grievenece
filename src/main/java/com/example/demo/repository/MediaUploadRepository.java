package com.example.demo.repository;

import com.example.demo.entity.MediaUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaUploadRepository extends JpaRepository<MediaUpload, Long> {
    List<MediaUpload> findByComplaintId(Long complaintId);
}
