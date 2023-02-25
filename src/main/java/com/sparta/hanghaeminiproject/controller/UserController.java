package com.sparta.hanghaeminiproject.controller;

import com.sparta.hanghaeminiproject.dto.LoginRequestDto;
import com.sparta.hanghaeminiproject.dto.SignupRequestDto;
import com.sparta.hanghaeminiproject.dto.StatusResponseDto;
import com.sparta.hanghaeminiproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

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
    public StatusResponseDto<String> login(@RequestBody LoginRequestDto loginRequestDto){
        return userService.login(loginRequestDto);
    }
}
