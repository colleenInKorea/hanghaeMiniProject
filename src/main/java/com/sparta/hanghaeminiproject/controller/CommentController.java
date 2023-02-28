package com.sparta.hanghaeminiproject.controller;

import com.sparta.hanghaeminiproject.dto.CommentRequestDto;
import com.sparta.hanghaeminiproject.dto.CommentResponseDto;
import com.sparta.hanghaeminiproject.dto.StatusResponseDto;
import com.sparta.hanghaeminiproject.security.UserDetailsImpl;
import com.sparta.hanghaeminiproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment/{id}")
    public StatusResponseDto<CommentResponseDto> createComment(@PathVariable Long id, String content, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.createComment(id, content, userDetails);
    }

    @PutMapping("/comment/{id}")
    public StatusResponseDto<CommentResponseDto> updateComment(@PathVariable Long id, CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.updateComment(id, commentRequestDto, userDetails);
    }

    @DeleteMapping("/comment/{id}")
    public StatusResponseDto<String> deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.deleteComment(id, userDetails);
    }

    @GetMapping("/comment/{projectId}")
    public StatusResponseDto<List<CommentResponseDto>> getComments(@PathVariable Long projectId){
        return commentService.getComments(projectId);
    }

//    @PostMapping("/comment/like/{id}")
//    public StatusResponseDto<String> likeComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
//        return commentService.likeComment(id, userDetails);
//    }

}
