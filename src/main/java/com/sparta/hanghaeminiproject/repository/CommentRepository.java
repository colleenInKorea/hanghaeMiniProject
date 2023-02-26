package com.sparta.hanghaeminiproject.repository;

import com.sparta.hanghaeminiproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByProjectId(Long id);
}
