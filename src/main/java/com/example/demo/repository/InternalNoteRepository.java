package com.example.demo.repository;

import com.example.demo.entity.InternalNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternalNoteRepository extends JpaRepository<InternalNote, Long> {
    List<InternalNote> findByComplaintIdOrderByCreatedAtDesc(Long complaintId);
    List<InternalNote> findByCreatedBy(String createdBy);
}
