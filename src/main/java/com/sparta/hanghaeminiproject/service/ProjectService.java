package com.sparta.hanghaeminiproject.service;

import com.sparta.hanghaeminiproject.dto.ProjectRequestDto;
import com.sparta.hanghaeminiproject.dto.ProjectResponseDto;
import com.sparta.hanghaeminiproject.dto.StatusResponseDto;
import com.sparta.hanghaeminiproject.entity.Project;
import com.sparta.hanghaeminiproject.entity.User;
import com.sparta.hanghaeminiproject.entity.UserRoleEnum;
import com.sparta.hanghaeminiproject.repository.ProjectRepository;
import com.sparta.hanghaeminiproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    //전체 프로젝트 조회
    public StatusResponseDto<List<ProjectResponseDto>> findProjects(){
        List<Project> lists = projectRepository.findAll();
        List<ProjectResponseDto> projectResponseDtos = new ArrayList<>();
        for(Project project : lists){
            projectResponseDtos.add(new ProjectResponseDto(project));
        }
        return StatusResponseDto.success(projectResponseDtos);
    }

    //프로젝트 생성하기
    public StatusResponseDto<ProjectResponseDto> createdProject(ProjectRequestDto projectRequestDto, UserDetailsImpl userDetails){
        Project project = new Project(projectRequestDto, userDetails.getUser());

        projectRepository.save(project);
        return StatusResponseDto.success(new ProjectResponseDto(project));
    }

    //선택 프로젝트 조회
    public StatusResponseDto<ProjectResponseDto> findProject(Long projectId){
        ProjectResponseDto projectResponseDto = new ProjectResponseDto(projectRepository.findById(projectId).orElseThrow(
                ()-> new IllegalArgumentException("Can not find project")
        ));
        return StatusResponseDto.success(projectResponseDto);
    }

    //선택 프로젝트 수정
    @Transactional
    public StatusResponseDto<ProjectResponseDto> updateProject(Long projectId, ProjectRequestDto requestDto, UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        Project project = projectRepository.findById(projectId).orElseThrow(
                ()-> new IllegalArgumentException("It doesn't exist this project")
        );
        if (user.getRole() == UserRoleEnum.ADMIN || user.getUsername().equals(project.getUser().getUsername())){
            project.update(requestDto);
            return StatusResponseDto.success(new ProjectResponseDto(project));
        }else {
            throw  new IllegalArgumentException("작성자만 수정가능합니다.");
        }
    }

    // 선택 프로젝트 삭제
    public StatusResponseDto<String> removeProject(Long projectId, UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        Project project = projectRepository.findById(projectId).orElseThrow(
                ()-> new NullPointerException("존재하지 않은 프로젝트")
        );

        if (user.getRole() == UserRoleEnum.ADMIN || user.getUsername().equals(project.getUser().getUsername())){
            projectRepository.deleteById(projectId);
        }else {
            throw  new IllegalArgumentException("작성자만 삭제가 가능합니다.");
        }
        return StatusResponseDto.success("delete success");
    }

}
