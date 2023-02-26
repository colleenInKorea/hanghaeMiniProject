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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ProjectRepository projectRepository;
//    private final CommentLikeRepository commentLikeRepository;

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

//    public StatusResponseDto<String> likeComment(Long id, UserDetailsImpl userDetails) {
//        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NullPointerException("존재하지 않는 댓글"));
//        Optional<CommentLike> optionalCommentLike = commentLikeRepository.findByCommentAndUser(comment, userDetails.getUser());
//        if (optionalCommentLike.isPresent()) { // 유저가 이미 좋아요를 눌렀을 때
//            commentLikeRepository.deleteById(optionalCommentLike.get().getId());
//            return StatusResponseDto.success("댓글 좋아요 취소");
//        }
//        commentLikeRepository.save(new CommentLike(comment, userDetails.getUser()));
//        return StatusResponseDto.success("댓글 좋아요 성공");
//    }
}