package com.example.demo.repository;

import com.example.demo.entity.UserReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserReplyRepository extends JpaRepository<UserReply, Long> {
    List<UserReply> findByComplaintIdOrderByRepliedAtDesc(Long complaintId);
    List<UserReply> findByRepliedBy(String repliedBy);
}
