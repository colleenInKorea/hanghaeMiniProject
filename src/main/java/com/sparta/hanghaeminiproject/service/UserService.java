package com.sparta.hanghaeminiproject.service;

import com.sparta.hanghaeminiproject.dto.LoginRequestDto;
import com.sparta.hanghaeminiproject.dto.SignupRequestDto;
import com.sparta.hanghaeminiproject.dto.StatusResponseDto;
import com.sparta.hanghaeminiproject.entity.User;
import com.sparta.hanghaeminiproject.jwt.JwtUtil;
import com.sparta.hanghaeminiproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private  final JwtUtil jwtUtil;

    @Transactional
    public StatusResponseDto<String> signup(SignupRequestDto signupRequestDto) {
        Optional<User> found = userRepository.findByUsername(signupRequestDto.getUsername());
        //회원 중복 확인
        if(found.isPresent()){
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        //사용자 등록
        User user = User.builder()
                .requestDto(signupRequestDto)
                .build();
        userRepository.save(user);

        return  StatusResponseDto.success("success SignUp!") ;
    }

    //    @Transactional(readOnly = true)
    public ResponseEntity<StatusResponseDto<String>> login(LoginRequestDto loginRequestDto) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        //사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                ()-> new IllegalArgumentException("회원을 찾을 수 없습니다.")
        );

        if (!user.getPassword().equals(password)){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        String token = jwtUtil.createToken(user.getUsername(), user.getRole());
        responseHeaders.set("Authorization",token);

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(StatusResponseDto.success("sucess login!"));
    }

}
