package com.sparta.hanghaeminiproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequestDto {

    private String title;

    private String content;

    private MultipartFile multipartFile;// 이미지 받을 공간

    private int backEndMember;

    private int frontEndMember;

    private List<String> stacks;


}
