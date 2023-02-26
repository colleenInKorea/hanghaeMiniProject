package com.sparta.hanghaeminiproject.controller;

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

    private final S3Uploader s3Uploader;
    private final ProjectService projectService;

//  전체 프로젝트 확인
    @GetMapping("/project")
    public StatusResponseDto<List<ProjectResponseDto>> getProjects(){
        return projectService.findProjects();
    }

//  프로젝트 등록
    @PostMapping("/project")
    public StatusResponseDto<ProjectResponseDto> createdProject(@RequestBody ProjectRequestDto projectRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return projectService.createdProject(projectRequestDto, userDetails);
    }

    //선택 프로젝트 확인
    @GetMapping("/project/{projectId}")
    public StatusResponseDto<ProjectResponseDto> getProject(@PathVariable Long projectId){
        return projectService.findProject(projectId);
    }

    //선택 프로젝트 수정
    @PutMapping("/project/{projectId}")
    public StatusResponseDto<ProjectResponseDto> updateProject(@PathVariable Long projectId, @RequestBody ProjectRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return projectService.updateProject(projectId, requestDto, userDetails);
    }

    //선택 프로젝트 삭제
    @DeleteMapping("/project/{projectId}")
    public StatusResponseDto<String> deleteProject(@PathVariable Long projectId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return projectService.removeProject(projectId, userDetails);
    }

    @PostMapping("/post")
    public String postImg (@RequestParam(value = "img") MultipartFile multipartFile) throws IOException {
        return s3Uploader.uploadFiles(multipartFile, "버킷 폴더이름");
    }
}
