package com.sparta.hanghaeminiproject.repository;

import com.sparta.hanghaeminiproject.entity.Project;
import com.sparta.hanghaeminiproject.entity.ProjectLike;
import com.sparta.hanghaeminiproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectLikeRepository extends JpaRepository<ProjectLike, Long> {
    Optional<ProjectLike> findByProjectAndUser(Project project, User user);
}
