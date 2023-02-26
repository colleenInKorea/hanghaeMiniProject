package com.sparta.hanghaeminiproject.dto;

import com.sparta.hanghaeminiproject.entity.Comment;
import com.sparta.hanghaeminiproject.entity.Project;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ProjectResponseDto {

    private Long id;
    private String title;
    private String username;
    private String content;
    private String image;
    private int backEndMember;

    private int frontEndMember;

    private List<String> stacks = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private List<CommentResponseDto> commentResponseDtos = new ArrayList<>();

    @Builder
    public ProjectResponseDto(Project project){
        this.id = project.getId();
        this.title = project.getTitle();
        this.username = project.getUser().getUsername();
        this.content = project.getContents();
        this.image = project.getImage();
        this.backEndMember = project.getBackEndMember();
        this.frontEndMember = project.getFrontEndMember();
//        this.stacks = project.getStacks();
        this.createdAt = project.getCreatedAt();
        this.modifiedAt = project.getModifiedAt();
//        for (Comment comment: project.getComments()){
//            this.commentResponseDtos.add(CommentResponseDto.from(comment));
//        }
    }
}
