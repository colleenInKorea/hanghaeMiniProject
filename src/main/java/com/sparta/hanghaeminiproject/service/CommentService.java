package com.sparta.hanghaeminiproject.service;

import com.sparta.hanghaeminiproject.dto.CommentRequestDto;
import com.sparta.hanghaeminiproject.dto.CommentResponseDto;
import com.sparta.hanghaeminiproject.dto.StatusResponseDto;
import com.sparta.hanghaeminiproject.entity.Comment;
import com.sparta.hanghaeminiproject.entity.Project;
import com.sparta.hanghaeminiproject.entity.User;
import com.sparta.hanghaeminiproject.entity.UserRoleEnum;
import com.sparta.hanghaeminiproject.repository.CommentRepository;
import com.sparta.hanghaeminiproject.repository.ProjectRepository;
import com.sparta.hanghaeminiproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public StatusResponseDto<CommentResponseDto> createComment(Long id, String content, UserDetailsImpl userDetails) {
// id는 프로젝트 아이디입니다.
        Project project = projectRepository.findById(id).orElseThrow(() -> new NullPointerException("등록되지 않은 게시글입니다."));
        Comment comment = new Comment(userDetails.getUser(), project, content);
        commentRepository.save(comment);
        return StatusResponseDto.success(new CommentResponseDto(comment));
    }


    @Transactional
    public StatusResponseDto<CommentResponseDto> updateComment(Long id, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NullPointerException("해당 댓글을 찾을 수 없습니다."));

        if (user.getRole() == UserRoleEnum.ADMIN || user.getUsername().equals(comment.getUser().getUsername())) {
            comment.update(commentRequestDto);
            return StatusResponseDto.success(new CommentResponseDto(comment));
        } else {
            throw new IllegalArgumentException("작성자만 수정이 가능합니다.");
        }
    }

    public StatusResponseDto<String> deleteComment(Long id, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new NullPointerException("댓글을 찾을 수 없음")
        );
        if (user.getRole() == UserRoleEnum.ADMIN || user.getUsername().equals(comment.getUser().getUsername())) {
            commentRepository.deleteById(id);
            return StatusResponseDto.success("delete success!");
        } else {
            throw new IllegalArgumentException("작성자만 수정이 가능합니다.");
        }
    }

    public StatusResponseDto<List<CommentResponseDto>> getComments(Long projectId){
        List<Comment> list = commentRepository.findAllByProjectId(projectId);
        List<CommentResponseDto> responseDtos = new ArrayList<>();
        for(Comment comment : list){
            responseDtos.add(CommentResponseDto.from(comment));
        }
        return StatusResponseDto.success(responseDtos);
    }
}