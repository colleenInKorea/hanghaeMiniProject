package com.sparta.hanghaeminiproject.service;

import com.sparta.hanghaeminiproject.dto.ProjectOneResponseDto;
import com.sparta.hanghaeminiproject.dto.ProjectRequestDto;
import com.sparta.hanghaeminiproject.dto.ProjectResponseDto;
import com.sparta.hanghaeminiproject.dto.StatusResponseDto;
import com.sparta.hanghaeminiproject.entity.Project;
import com.sparta.hanghaeminiproject.entity.ProjectLike;
import com.sparta.hanghaeminiproject.entity.User;
import com.sparta.hanghaeminiproject.entity.UserRoleEnum;
import com.sparta.hanghaeminiproject.repository.ProjectLikeRepository;
import com.sparta.hanghaeminiproject.repository.ProjectRepository;
import com.sparta.hanghaeminiproject.s3.S3Uploader;
import com.sparta.hanghaeminiproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//import static java.util.stream.Nodes.collect;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final ProjectLikeRepository projectLikeRepository;

    private final S3Uploader s3Uploader;


//    전체 프로젝트 조회
    public StatusResponseDto<List<ProjectOneResponseDto>> findProjects(){
        List<Project> lists = projectRepository.findAll();
        List<ProjectOneResponseDto> projectOneResponseDtos = new ArrayList<>();
        for(Project project : lists){
            projectOneResponseDtos.add(ProjectOneResponseDto.of(project));
        }
        return StatusResponseDto.success(projectOneResponseDtos);
    }


    //프로젝트 생성하기
    public StatusResponseDto<ProjectResponseDto> createdProject( ProjectRequestDto projectRequestDto, UserDetailsImpl userDetails) throws IOException {
        String imageUrl = s3Uploader.uploadFiles(projectRequestDto.getMultipartFile(), "images");
        Project project = new Project(projectRequestDto, userDetails.getUser(), imageUrl);

        projectRepository.save(project);
        return StatusResponseDto.success(new ProjectResponseDto(project));
    }

    //선택 프로젝트 조회
    public StatusResponseDto<ProjectResponseDto> findProject(Long projectId, UserDetailsImpl userDetails){
        ProjectResponseDto projectResponseDto = new ProjectResponseDto(projectRepository.findById(projectId).orElseThrow(
                ()-> new IllegalArgumentException("Can not find project")
        ));
        Project project = projectRepository.findById(projectId).orElseThrow(
                ()-> new EntityNotFoundException("해당 프로젝트를 찾을 수 없습니다.")
        );
        Optional<ProjectLike> projectLike = projectLikeRepository.findByProjectAndUser(project, userDetails.getUser());

        if(projectLike.isPresent()){
            return StatusResponseDto.success(projectResponseDto, true) ;
        }
        return StatusResponseDto.success(projectResponseDto, false);
    }

    //선택 프로젝트 수정
    @Transactional
    public StatusResponseDto<ProjectResponseDto> updateProject(Long projectId, ProjectRequestDto requestDto, UserDetailsImpl userDetails) throws IOException {
        User user = userDetails.getUser();
        String imageUrl = s3Uploader.uploadFiles(requestDto.getMultipartFile(), "images");
        Project project = projectRepository.findById(projectId).orElseThrow(
                ()-> new IllegalArgumentException("It doesn't exist this project")
        );
        if (user.getRole() == UserRoleEnum.ADMIN || user.getUsername().equals(project.getUser().getUsername())){
            project.update(requestDto, imageUrl);
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

    //좋아요 만들기
    public StatusResponseDto<String> likeProject(Long boardId, UserDetailsImpl userDetails) {
        Project project = projectRepository.findById(boardId).orElseThrow(
                ()-> new NullPointerException("존재하지 않는 프로젝트입니다.")
        );

        Optional<ProjectLike> projectLike = projectLikeRepository.findByProjectAndUser(project, userDetails.getUser());
        if(projectLike.isPresent()){
            projectLikeRepository.deleteById((projectLike.get().getId()));
            return StatusResponseDto.success("해당 프로젝트에 좋아요가 취소 되었습니다.", false);
        }

        projectLikeRepository.save(new ProjectLike(project, userDetails.getUser()));
        return StatusResponseDto.success("해당 프로젝트에 좋아요가 추가 되었습니다.", true);
    }
}
