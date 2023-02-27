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
    private String imageUrl;
    private int backEndMember;

    private int frontEndMember;
    //    private List<String> stacks = new ArrayList<>();
    private String backEndStack;
    private String frontEndStack;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private int likeCount;

    private List<CommentResponseDto> commentResponseDtos = new ArrayList<>();

    @Builder
    public ProjectResponseDto(Project project) {
        this.id = project.getId();
        this.title = project.getTitle();
        this.username = project.getUser().getUsername();
        this.content = project.getContents();
        this.imageUrl = project.getImageUrl();
        this.backEndMember = project.getBackEndMember();
        this.frontEndMember = project.getFrontEndMember();
//        this.stacks = project.getStacks();
        this.backEndStack = project.getBackEndStack();
        this.frontEndStack = project.getFrontEndStack();
        this.likeCount = project.getLikes().size();
        this.createdAt = project.getCreatedAt();
        this.modifiedAt = project.getModifiedAt();
        for (Comment comment : project.getCommentList()) {
            this.commentResponseDtos.add(CommentResponseDto.from(comment));
        }
    }
}
