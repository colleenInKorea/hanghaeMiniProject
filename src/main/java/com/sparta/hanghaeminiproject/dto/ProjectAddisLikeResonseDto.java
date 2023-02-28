package com.sparta.hanghaeminiproject.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProjectAddisLikeResonseDto {
    private ProjectResponseDto projectResponseDto;

    private boolean isLike;
    public ProjectAddisLikeResonseDto(ProjectResponseDto projectResponseDto, boolean isLike){
        this.projectResponseDto = projectResponseDto;
        this.isLike = isLike;
    }
}
