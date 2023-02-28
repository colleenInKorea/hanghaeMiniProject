package com.sparta.hanghaeminiproject.repository;

import com.sparta.hanghaeminiproject.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByOrderByModifiedAtDesc();

}
