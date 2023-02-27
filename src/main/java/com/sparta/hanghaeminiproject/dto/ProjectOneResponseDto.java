package com.sparta.hanghaeminiproject.dto;

import com.sparta.hanghaeminiproject.entity.Project;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProjectOneResponseDto {

    private Long id;
    private String title;
    private String username;
    private String content;

//    @Builder
    public static ProjectOneResponseDto of(Project project){
        return ProjectOneResponseDto.builder()
                .id(project.getId())
                .title(project.getTitle())
                .content(project.getContents())
                .username(project.getUser().getUsername())
                .build();
    }

}
