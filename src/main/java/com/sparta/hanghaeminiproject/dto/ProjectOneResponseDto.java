package com.sparta.hanghaeminiproject.dto;

import com.sparta.hanghaeminiproject.entity.Project;
import lombok.Builder;

public class ProjectOneResponseDto {

    private Long id;
    private String title;
    private String username;
    private String content;

    @Builder
    public ProjectOneResponseDto(Project project){
        this.id = project.getId();
        this.username = project.getUser().getUsername();
        this.title = project.getTitle();
        this.content = project.getContents();
    }

}
