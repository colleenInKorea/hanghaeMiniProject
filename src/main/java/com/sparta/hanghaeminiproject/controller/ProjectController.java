package com.sparta.hanghaeminiproject.controller;

import com.sparta.hanghaeminiproject.dto.ProjectOneResponseDto;
import com.sparta.hanghaeminiproject.dto.ProjectRequestDto;
import com.sparta.hanghaeminiproject.dto.ProjectResponseDto;
import com.sparta.hanghaeminiproject.dto.StatusResponseDto;
import com.sparta.hanghaeminiproject.security.UserDetailsImpl;
import com.sparta.hanghaeminiproject.service.ProjectService;
import com.sparta.hanghaeminiproject.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProjectController {

    private final ProjectService projectService;

//    //  전체 프로젝트 확인
//    @GetMapping("/project")
//    public StatusResponseDto<List<ProjectOneResponseDto>> getProjects() {
//        return projectService.findProjects();
//    }

    //  프로젝트 등록
    @PostMapping("/project")
    public StatusResponseDto<ProjectResponseDto> createdProject(@RequestParam(value = "title") String title,
                                                                @RequestParam(value = "content") String content,
                                                                @RequestParam(value = "backEndMember") int backEndMember,
                                                                @RequestParam(value = "frontEndMember") int frontEndMember,
                                                                @RequestParam(value = "stacks") List<String> stacks,
                                                                @RequestParam(value = "image") MultipartFile multipartFile,
                                                                @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        ProjectRequestDto projectRequestDto = new ProjectRequestDto(title, content, multipartFile, backEndMember, frontEndMember, stacks);
        return projectService.createdProject(projectRequestDto, userDetails);
    }

    //선택 프로젝트 확인
    @GetMapping("/project/{projectId}")
    public StatusResponseDto<ProjectResponseDto> getProject(@PathVariable Long projectId) {
        return projectService.findProject(projectId);
    }

    //선택 프로젝트 수정
    @PutMapping("/project/{projectId}")
    public StatusResponseDto<ProjectResponseDto> updateProject(@PathVariable Long projectId,
                                                               @RequestParam(value = "title") String title,
                                                               @RequestParam(value = "content") String content,
                                                               @RequestParam(value = "backEndMember") int backEndMember,
                                                               @RequestParam(value = "frontEndMember") int frontEndMember,
                                                               @RequestParam(value = "stacks") List<String> stacks,
                                                               @RequestParam(value = "image") MultipartFile multipartFile,
                                                               @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        ProjectRequestDto requestDto = new ProjectRequestDto(title, content, multipartFile, backEndMember, frontEndMember, stacks);
        return projectService.updateProject(projectId, requestDto, userDetails);
    }

    //선택 프로젝트 삭제
    @DeleteMapping("/project/{projectId}")
    public StatusResponseDto<String> deleteProject(@PathVariable Long projectId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return projectService.removeProject(projectId, userDetails);
    }

    @PostMapping("/project/like/{boardId}")
    public StatusResponseDto<String> likeProject(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return projectService.likeProject(boardId, userDetails);
    }
}
