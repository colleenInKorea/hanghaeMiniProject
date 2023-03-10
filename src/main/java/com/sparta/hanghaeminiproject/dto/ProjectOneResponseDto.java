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
    private int likeCount;
    private String createdAt;
    private String imageUrl;
    private int backEndMember;
    private int frontEndMember;

    public static ProjectOneResponseDto of(Project project){
        return ProjectOneResponseDto.builder()
                .id(project.getId())
                .title(project.getTitle())
                .content(project.getContents())
                .username(project.getUser().getUsername())
                .imageUrl(project.getImageUrl())
                .backEndMember(project.getBackEndMember())
                .frontEndMember(project.getFrontEndMember())
                .likeCount(project.getLikes().size())
                .createdAt(project.getCreatedAt().toString())
                .build();
    }

}
