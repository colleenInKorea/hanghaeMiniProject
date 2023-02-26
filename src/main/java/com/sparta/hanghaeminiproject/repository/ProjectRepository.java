package com.sparta.hanghaeminiproject.repository;

import com.sparta.hanghaeminiproject.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
