package com.sparta.hanghaeminiproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ProjectRequestDto {

    private String title;

    private String content;

    private String image;

    private int backEndMember;

    private int frontEndMember;

    private List<String> stacks;


}
