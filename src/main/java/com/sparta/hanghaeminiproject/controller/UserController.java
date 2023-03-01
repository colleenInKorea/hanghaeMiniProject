package com.sparta.hanghaeminiproject.controller;

import com.sparta.hanghaeminiproject.dto.*;
import com.sparta.hanghaeminiproject.entity.User;
import com.sparta.hanghaeminiproject.security.UserDetailsImpl;
import com.sparta.hanghaeminiproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @ResponseBody
    @PostMapping("/signup")
    public StatusResponseDto<String> signup(@Valid @RequestBody SignupRequestDto signupRequestDto){
        return userService.signup(signupRequestDto);
    }

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<StatusResponseDto<User>> login(@RequestBody LoginRequestDto loginRequestDto){
        return userService.login(loginRequestDto);
    }

    @ResponseBody
    @PutMapping("/{userId}")
    public  StatusResponseDto<User> update(@PathVariable Long userId, @RequestBody UserRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.update(userId, requestDto, userDetails);
    }

    @ResponseBody
    @GetMapping ("/{userId}")
    public StatusResponseDto<UserResponseDto> getUserInfo(@PathVariable Long userId){
        return userService.getUserInfo(userId);
    }

    @ResponseBody
    @GetMapping("/")
    public List<UserOneResponseDto> getUsers(){
        return userService.getUsers();
    }


}
