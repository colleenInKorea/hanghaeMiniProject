package com.sparta.hanghaeminiproject.service;

import com.sparta.hanghaeminiproject.dto.*;
import com.sparta.hanghaeminiproject.entity.User;
import com.sparta.hanghaeminiproject.entity.UserRoleEnum;
import com.sparta.hanghaeminiproject.jwt.JwtUtil;
import com.sparta.hanghaeminiproject.repository.UserRepository;
import com.sparta.hanghaeminiproject.security.UserDetailsImpl;
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

    //회원 가입
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

    //로그인
    //    @Transactional(readOnly = true)
    public ResponseEntity<StatusResponseDto<User>> login(LoginRequestDto loginRequestDto) {
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
//        responseHeaders.add("userId", user.getId().toString());

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(StatusResponseDto.success(user));
    }

    //회원 정보 수정
    @Transactional
    public StatusResponseDto<User> update(Long userId, UserRequestDto requestDto, UserDetailsImpl userDetails) {
        String introduction = requestDto.getIntroduction();
        String part = requestDto.getPart();
        String nickname = requestDto.getNickname();

        //check user info
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
        );

        if (user.getRole() == UserRoleEnum.ADMIN || user.getUsername().equals(userDetails.getUser().getUsername())){
            user.update(part, introduction, nickname);
            return StatusResponseDto.success(user);
        }else {
            throw new IllegalArgumentException("해당 유저만 수정가능합니다.");
        }
    }

    @Transactional(readOnly = true)
    public StatusResponseDto<UserResponseDto> getUserInfo(Long userId){
        UserResponseDto userResponseDto = new UserResponseDto(userRepository.findById(userId).orElseThrow(
                ()-> new IllegalArgumentException("can not find userInfo")
        ));
        return StatusResponseDto.success(userResponseDto);
    }
}
